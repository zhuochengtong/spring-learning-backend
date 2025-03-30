package itzhuo.system.dao.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 自动生成带所有字段的构造函数
public class FileInfoVO {
    private String fileName;
    private long size;
}
