import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:my_poster_example/main.dart';

void main() {
  testWidgets('Verify Platform version', (WidgetTester tester) async {
    await tester.pumpWidget(MyApp());

    expect(
      find.byWidgetPredicate(
        (Widget widget) =>
            widget is Text && widget.data.startsWith('Running on:'),
      ),
      findsOneWidget,
    );
  });
}
