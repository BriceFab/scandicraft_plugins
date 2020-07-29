@echo on 

set libs_path=./libraries
set extra_libs_path=./build/extra-libs
set main_jar=./build/out.jar

"D:\Programs\Program Files\java\jdk1.8.0_251\bin\java" -Djava.library.path=natives -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xms1024M -Xmx1024M -cp ^
%libs_path%\authlib-1.5.21.jar;^
%libs_path%\codecjorbis-20101023.jar;^
%libs_path%\codecwav-20101023.jar;^
%libs_path%\commons-codec-1.9.jar;^
%libs_path%\commons-compress-1.8.1.jar;^
%libs_path%\commons-io-2.4.jar;^
%libs_path%\commons-lang3-3.3.2.jar;^
%libs_path%\commons-logging-1.1.3.jar;^
%libs_path%\gson-2.2.4.jar;^
%libs_path%\guava-17.0.jar;^
%libs_path%\httpclient-4.3.3.jar;^
%libs_path%\httpcore-4.3.2.jar;^
%libs_path%\icu4j-core-mojang-51.2.jar;^
%libs_path%\jinput-2.0.5.jar;^
%libs_path%\jinput-platform-2.0.5-natives-windows.jar;^
%libs_path%\jna-3.4.0.jar;^
%libs_path%\jopt-simple-4.6.jar;^
%libs_path%\jutils-1.0.0.jar;^
%libs_path%\libraryjavasound-20101123.jar;^
%libs_path%\librarylwjglopenal-20100824.jar;^
%libs_path%\log4j-api-2.0-beta9.jar;^
%libs_path%\log4j-core-2.0-beta9.jar;^
%libs_path%\lwjgl_util-2.9.4-nightly-20150209.jar;^
%libs_path%\lwjgl-2.9.4-nightly-20150209.jar;^
%libs_path%\lwjgl-platform-2.9.4-nightly-20150209-natives-windows.jar;^
%libs_path%\netty-1.6.jar;^
%libs_path%\netty-all-4.0.23.Final.jar;^
%libs_path%\oshi-core-1.1.jar;^
%libs_path%\platform-3.4.0.jar;^
%libs_path%\realms-1.7.39.jar;^
%libs_path%\soundsystem-20120107.jar;^
%libs_path%\trove-3.0.2.jar;^
%libs_path%\twitch-6.5.jar;^
%libs_path%\twitch-external-platform-4.5-natives-windows-64.jar;^
%libs_path%\twitch-platform-6.5-natives-windows-64.jar;^
%libs_path%\vecmath.jar;^
%libs_path%\discord-rpc.jar;^
%extra_libs_path%\kotlin-stdlib-1.3.61.jar;^
%extra_libs_path%\annotations-13.0.jar;^
%main_jar% net.scandicraft.client.Main --username=BriceFab123 --password=DguV9vq98fpASZvqCVd0 --accessToken null --version 1.8 --gameDir . --assetsDir ./assets --assetIndex 1.8 --userProperties {} --uuid null --userType legacy

::PAUSE