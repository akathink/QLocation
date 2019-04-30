# QLocation
Android Q定位权限变更验证

## Android Q以前的定位权限只有：

ACCESS_FINE_LOCATION 和 ACCESS_COARSE_LOCATION

## Android Q新增了：

ACCESS_BACKGROUND_LOCATION

针对前台服:foregroundServiceType=“location”

## 注意事项

GPS只能室外进行测试；

网络定位，必须开启VPN，能够访问谷歌服务方可，否则获取的地理位置为空。

使用后台定位权限之前，需要先申请前台定位权限，否则会自动拒绝

如果当前处于前台位置，地理位置并获取成功，此时再退入后台，依旧可以获取到地理位置信息，也就是上次获取到的缓存值；

如果仅有前台位置权限，并且APP当前没有获取地理位置信息，那么退入后台，同样是获取不到地理位置信息的。
