package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.common.utils.JsonUtil;
import itzhuo.system.dao.model.system.SystemMenuCrFrom;

import static org.junit.jupiter.api.Assertions.*;

class SystemMenuControllerTest {

    public static void main(String[] args) {
        try {
            String json = "{\n" +
                    "    \"id\": \"dsakdasjhdkhj\",\n" +
                    "    \"parentId\": \"0\",\n" +
                    "    \"name\": \"sy\",\n" +
                    "    \"title\": \"权限管理\",\n" +
                    "    \"sort\": 3,\n" +
                    "    \"icon\": null,\n" +
                    "    \"path\": \"/permission\",\n" +
                    "    \"component\": \"PermissionManage\",\n" +
                    "    \"perms\": null,\n" +
                    "    \"status\": 1\n" +
                    "}\n";
            SystemMenuCrFrom bean = JsonUtil.getJsonToBean(json, SystemMenuCrFrom.class);
            System.out.println(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}