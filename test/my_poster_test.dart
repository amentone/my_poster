import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_poster/my_poster.dart';

void main() {
  const MethodChannel channel = MethodChannel('my_poster');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await MyPoster.platformVersion, '42');
  });
}
