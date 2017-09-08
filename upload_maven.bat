
%打包%
%gradlew assembleRelease --stacktrace >print.log%

%上传全部module%
gradlew uploadArchives --stacktrace >print.log

%注释：单个模块上传把moduleName改成自己的项目名称：-p {moduleName}%
%gradlew uploadArchives -p library_maven_test --stacktrace >print.log%