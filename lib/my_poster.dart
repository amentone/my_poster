
import 'dart:async';

import 'package:flutter/services.dart';

class MyPoster {
  static const MethodChannel _channel =
      const MethodChannel('my_poster');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
