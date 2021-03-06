package cn.tim.annotation_processor.config;

public interface ProcessorConfig {
    String ROUTER_PACKAGE_NAME = "cn.tim.annotation.ARouter";

    String MODULE_NAME = "moduleName";

    String APT_PACKAGE_NAME = "packageNameForAPT";

    String ACTIVITY_PACKAGE_NAME = "android.app.Activity";

    String ROUTER_API_PACKAGE_NAME = "cn.tim.router_api";

    String ROUTER_API_GROUP = ROUTER_API_PACKAGE_NAME + ".ARouterGroup";

    String ROUTER_API_PATH = ROUTER_API_PACKAGE_NAME + ".ARouterPath";

    String PATH_METHOD_NAME = "getPathMap";

    String GROUP_METHOD_NAME = "getGroupMap";

    String PATH_VAR = "pathMap";

    String GROUP_VAR = "groupMap";

    String PATH_FILE_NAME = "ARouter$$Path$$";

    String GROUP_FILE_NAME = "ARouter$$Group$$";
}
