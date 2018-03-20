
% 上传全部module%
% gradlew uploadArchives --stacktrace >print.log %

% 注释：单个模块上传把moduleName改成自己的项目名称：-p {moduleName} %
% gradlew uploadArchives -p {moduleName} --stacktrace %

% 注释：打包 %
% gradlew clean %
% gradlew assembleTestingDebug -p app_distribution --stacktrace %