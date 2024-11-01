import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import 'package:vote_sync/config/app_colors.dart';

class QrCodeScannerPage extends StatefulWidget {
  const QrCodeScannerPage({super.key});

  @override
  State<QrCodeScannerPage> createState() => _QrCodeScannerPageState();
}

class _QrCodeScannerPageState extends State<QrCodeScannerPage> {
  bool isFlashOn = false;
  MobileScannerController cameraController = MobileScannerController();

  @override
  void dispose() {
    cameraController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Scan Code QR'),
        centerTitle: true,
        actions: [
          IconButton(
            icon: Icon(
              isFlashOn ? Icons.flash_on : Icons.flash_off,
              color: isFlashOn ? Colors.yellow : Colors.white,
            ),
            onPressed: () {
              setState(() {
                isFlashOn = !isFlashOn;
              });
              cameraController.toggleTorch();
            },
          ),
        ],
      ),
      body: Stack(
        children: [
          MobileScanner(
            controller: cameraController,
            onDetect: (barcodeCapture) {
              for (Barcode barcode in barcodeCapture.barcodes) {
                if (barcode.rawValue != null) {
                  cameraController.dispose();
                  Navigator.pop(
                    context,
                    barcode.rawValue!,
                  );
                  setState(() {
                    cameraController = MobileScannerController();
                  });
                  break;
                }
              }
            },
          ),
          _scannerOverlay(),
        ],
      ),
    );
  }

  Widget _scannerOverlay() {
    return Center(
      child: Stack(
        children: [
          Container(
            margin: const EdgeInsets.all(30),
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.white,
                width: 2,
              ),
              borderRadius: BorderRadius.circular(12),
            ),
            child: Container(
              width: 250,
              height: 250,
              decoration: BoxDecoration(
                border: Border.all(color: AppColors.primaryGreen, width: 2),
                borderRadius: BorderRadius.circular(12),
              ),
            ),
          ),
          Positioned(
            top: 30,
            left: 30,
            right: 30,
            bottom: 30,
            child: SizedBox(
              width: 250,
              height: 250,
              child: CustomPaint(
                painter: _ScannerBorderPainter(),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class _ScannerBorderPainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    final Paint paint = Paint()
      ..color = AppColors.primaryGreen
      ..strokeWidth = 3
      ..style = PaintingStyle.stroke;

    const double lineLength = 20;

    // Draw top-left corner
    canvas.drawLine(const Offset(0, 0), const Offset(lineLength, 0), paint);
    canvas.drawLine(const Offset(0, 0), const Offset(0, lineLength), paint);

    // Draw top-right corner
    canvas.drawLine(
        Offset(size.width - lineLength, 0), Offset(size.width, 0), paint);
    canvas.drawLine(
        Offset(size.width, 0), Offset(size.width, lineLength), paint);

    // Draw bottom-left corner
    canvas.drawLine(
        Offset(0, size.height), Offset(lineLength, size.height), paint);
    canvas.drawLine(
        Offset(0, size.height), Offset(0, size.height - lineLength), paint);

    // Draw bottom-right corner
    canvas.drawLine(Offset(size.width - lineLength, size.height),
        Offset(size.width, size.height), paint);
    canvas.drawLine(Offset(size.width, size.height - lineLength),
        Offset(size.width, size.height), paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}
