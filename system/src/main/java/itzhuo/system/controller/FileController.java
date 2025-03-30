package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.system.dao.model.file.FileInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.FileInfo;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    // 修改后的存储配置
    private final String BASE_DIR = "D:\\upload\\"; // 统一存储路径

    // 并发控制（使用ReentrantLock）
    private static final ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();


    /**
     * 1、检查分片 ---- 检查已经上传了哪些分片，返回已存在的分片列表，并判断是否需要继续上传
     * @param fileHash 文件hash
     * @param total 分片总数
     * @return
     */
    @GetMapping("/check")
    public ActionResult<Map<String, Object>> checkChunks(
            @RequestParam String fileHash,
            @RequestParam int total) {

        // 构建了一个路径，基于BASE_DIR加上fileHash和"chunks"目录，例如：D:\\upload\\326c645b36dce0db3c0ac2f294afd939\\chunks
        Path chunkDir = Paths.get(BASE_DIR, fileHash, "chunks");
        // 新建一个列表，用于存放分索引
        List<Integer> existed = new ArrayList<>();
        // 遍历这个目录下的所有以chunk_开头、.tmp结尾的文件，提取出分片索引，存到existed列表里
        if (Files.exists(chunkDir)) {
            // 读取分片目录
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(chunkDir, "chunk_*.tmp")) { //chunk_0.tmp
                for (Path chunk : stream) {
                    log.info("chunk: {}", chunk);
                    String name = chunk.getFileName().toString();
                    int index = Integer.parseInt(name.replace("chunk_", "").replace(".tmp", ""));
                    existed.add(index);
                }
            } catch (IOException e) {
                throw new RuntimeException("分片目录读取失败");
            }
        }

        return ActionResult.success(Map.of(
                "existedChunks", existed,
                "needUpload", existed.size() < total
        ));
    }

    /**
     * 2、上传分片
     * @param chunk 分片
     * @param fileHash 文件hash
     * @param chunkIndex 分片索引
     * @param totalChunks 分片总数
     * @return
     */
    @PostMapping("/upload")
    public ActionResult<String> uploadChunk(
            @RequestParam("chunk") MultipartFile chunk,
            @RequestParam String fileHash,
            @RequestParam int chunkIndex,
            @RequestParam int totalChunks) {

        try {
            // 创建分片存储目录
            Path chunkDir = Paths.get(BASE_DIR, "chunks");
            Files.createDirectories(chunkDir);

            // 生成安全文件名
            String chunkName = String.format("chunk_%d.tmp", chunkIndex);
            // 组合形成一个完整的目标路径，生成分片文件路径
            Path targetPath = chunkDir.resolve(chunkName);

            // 写入分片
            chunk.transferTo(targetPath.toFile());
            return ActionResult.success("分片保存成功");
        } catch (IOException e) {
            return ActionResult.fail("分片保存失败");
        }
    }

    /**
     * 3、合并分片
     * @param fileHash
     * @param fileName
     * @param totalChunks
     * @return
     */
    @PostMapping("/merge")
    public ActionResult<String> mergeChunks(
            @RequestParam String fileHash,
            @RequestParam String fileName,
            @RequestParam int totalChunks) {
        // 防御路径遍历攻击
        if (fileName.contains("..") || fileName.contains("/")) {
            throw new IllegalArgumentException("非法文件名");
        }

        try {
            Path chunkDir = Paths.get(BASE_DIR, "chunks");
            Path outputPath = Paths.get(BASE_DIR, fileName);

            // 安全校验
            if (!Files.exists(chunkDir)) {
                return ActionResult.success("分片不存在");
            }


            Lock lock = locks.computeIfAbsent(fileHash, k -> new ReentrantLock());
            lock.lock();
            try (BufferedOutputStream bos = new BufferedOutputStream(
                    Files.newOutputStream(outputPath))) {
                // 合并操作
                // 按顺序合并分片
                for (int i = 0; i < totalChunks; i++) {
                    Path chunkPath = chunkDir.resolve("chunk_" + i + ".tmp");
                    if (!Files.exists(chunkPath)) {
                        throw new RuntimeException("缺失分片：" + i);
                    }
                    Files.copy(chunkPath, bos);
                }
            } finally {
                lock.unlock();
            }
            // 清理分片目录
            FileUtils.deleteDirectory(chunkDir.toFile());
            return ActionResult.success("合并成功");
        } catch (Exception e) {
            return ActionResult.fail("合并失败：" + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ActionResult<List<FileInfoVO>> viewFile(){
        try {
            // 获取文件路径
            String filePath = BASE_DIR;
            File directory = new File(filePath);

            // 检查路径是否存在
            if (!directory.exists()) {
                return ActionResult.fail("路径不存在：" + filePath);
            }

            // 检查是否是目录
            if (!directory.isDirectory()) {
                return ActionResult.fail("路径不是目录：" + filePath);
            }

            // 获取目录下的所有文件和文件夹
            File[] files = directory.listFiles();
            if (files == null) {
                return ActionResult.fail("无法读取目录内容：" + filePath);
            }

            // 将文件和文件夹名称存储到列表中
            List<FileInfoVO> fileList = new ArrayList<>();
            for (File file : files) {
                fileList.add(new FileInfoVO(file.getName(), file.length()));
            }

            // 返回成功结果
            return ActionResult.success("文件列表获取成功", fileList);
        } catch (Exception e) {
            return ActionResult.fail("查看文件失败：" + e.getMessage());
        }
    }
}