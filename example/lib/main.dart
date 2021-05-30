import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:my_poster/my_poster.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool print;

  @override
  void initState() {
    super.initState();
    print = true;
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    try {} on PlatformException {}
    if (!mounted) return;

    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('MyPos'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(16),
                child: Container(
                  color: Colors.blueAccent,
                  child: TextButton(
                    child: Text(
                      "$print stampa",
                      style: TextStyle(color: Colors.white),
                    ),
                    onPressed: () async {
                      setState(() {
                        print = !print;
                      });
                    },
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16),
                child: Container(
                  color: Colors.redAccent,
                  child: TextButton(
                    child: Text(
                      "Pagamento",
                      style: TextStyle(color: Colors.white),
                    ),
                    onPressed: () async {
                      await MyPoster.makePayment(10.00, print ? 1 : 2);
                    },
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16),
                child: Container(
                  color: Colors.black,
                  child: TextButton(
                    child: Text(
                      "Rimborso",
                      style: TextStyle(color: Colors.white),
                    ),
                    onPressed: () async {
                      await MyPoster.refund(8.50, print ? 1 : 2);
                    },
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
