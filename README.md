# phoenix-recorder
Java版的屏幕录制引擎，并能将录制后的视频压缩转换成wmv，mp4等格式
启动方法：phoenix.record.run.recorder.ScreenRecorderMain.start
停止方法：phoenix.record.run.recorder.ScreenRecorderMain.sttop

使用方法示例：
ScreenRecorderMain screenRecorderMain = new ScreenRecorderMain();
screenRecorderMain.start();
......
screenRecorderMain.stop();

实现原理

1.通过java自带的robot方法按一定频率进行截屏，先驻留内存中
2.首先按avi编码循环持续压制，持续存储到硬盘
3.使用ffmpeg.exe压缩转换成flv格式，压缩率30:1，即将30M的avi视频能压缩到1M的flv，且播放效果损失不太大
4.整个过程都是在后台完成
