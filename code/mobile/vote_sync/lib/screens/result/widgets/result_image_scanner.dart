import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';
import 'package:vote_sync/config/app_colors.dart';

class ResultImageScanner extends StatefulWidget {
  const ResultImageScanner({super.key});

  @override
  State<ResultImageScanner> createState() => _ResultImageScannerState();
}

class _ResultImageScannerState extends State<ResultImageScanner> {
  final ImagePicker _picker = ImagePicker();
  final List<File> _capturedImages = [];

  Future<void> _takePicture() async {
    final XFile? photo = await _picker.pickImage(source: ImageSource.camera);
    if (photo != null) {
      final File imageFile = File(photo.path);
      XFile? compressedImage = await _compressToWebP(imageFile);
      if (compressedImage != null) {
        imageFile.delete();
        setState(() {
          _capturedImages.add(File(compressedImage.path));
        });
      }
    }
  }

  Future<XFile?> _compressToWebP(File imageFile) async {
    final tempDir = await getTemporaryDirectory();
    final String targetPath =
        '${tempDir.path}/${DateTime.now().millisecondsSinceEpoch}.webp';
    return await FlutterImageCompress.compressAndGetFile(
      imageFile.absolute.path,
      targetPath,
      format: CompressFormat.webp,
      quality: 90,
      minWidth: 1200, // Resize width for A4/A3 paper (adjust as needed)
      minHeight: 1600, // Resize height for A4/A3 paper (adjust as needed)
    );
  }

  void _removeImage(int index) {
    setState(() {
      _capturedImages.removeAt(index);
    });
  }

  void _confirmImages() {
    Navigator.pop(context, _capturedImages);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Scan des résultats'),
      ),
      body: Column(
        children: [
          // Capture button
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: ElevatedButton.icon(
              onPressed: _takePicture,
              icon: const Icon(Icons.camera),
              label: const Text('Prendre une photo'),
            ),
          ),
          // Display captured images
          Expanded(
            child: _capturedImages.isEmpty
                ? const Center(child: Text("Prenez les photos."))
                : GridView.builder(
                    gridDelegate:
                        const SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 3,
                      mainAxisSpacing: 10,
                      crossAxisSpacing: 10,
                    ),
                    itemCount: _capturedImages.length,
                    itemBuilder: (context, index) {
                      return Stack(
                        children: [
                          Image.file(
                            _capturedImages[index],
                            fit: BoxFit.cover,
                            width: double.infinity,
                          ),
                          Positioned(
                            top: 5,
                            right: 5,
                            child: InkWell(
                              onTap: () => _removeImage(index),
                              child: Container(
                                color: Colors.black54,
                                padding: const EdgeInsets.all(4),
                                child: const Icon(
                                  Icons.delete,
                                  color: Colors.white,
                                  size: 20,
                                ),
                              ),
                            ),
                          ),
                        ],
                      );
                    },
                  ),
          ),
          // Confirm button
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.primaryGreen,
                foregroundColor: Colors.white,
              ),
              onPressed: _capturedImages.isNotEmpty ? _confirmImages : null,
              child: const Text("Confirmer"),
            ),
          ),
        ],
      ),
    );
  }
}
