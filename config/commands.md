
# 命令行集

上传全部模块
```gradle
gradlew uploadArchives --stacktrace >print.log
```

上传单个模块，${moduleName}为模块名称
```gradle
gradlew uploadArchives -p ${moduleName} --stacktrace
``` 

打包，${moduleName}为模块名称
```gradle
gradlew clean assembleTestingDebug -p ${moduleName} --stacktrace
```

参数打包命令流程
```gradle
gradlew clean 
// 配送端测试包
gradlew -POUT_PUT=outputs/ -PDEVICE_TYPE=android -PENV=beta assembleTestingDebug -p app_distribution 

// 配送端正式包
gradlew -POUT_PUT=outputs/ -PDEVICE_TYPE=android -PENV=stable assembleOfficialRelease -p app_distribution 

// 配送端PDA测试包
gradlew -POUT_PUT=outputs/ -PDEVICE_TYPE=pda -PENV=beta assembleTestingDebug -p app_distribution 

// 配送端PDA正式包
gradlew -POUT_PUT=outputs/ -PDEVICE_TYPE=pda -PENV=stable assembleOfficialRelease -p app_distribution 

```
