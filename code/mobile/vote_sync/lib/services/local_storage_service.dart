import 'dart:io';

class LocalStorageService {
  final Directory appDocDir;

  const LocalStorageService({required this.appDocDir});

  Future<File> save(
    String filePath,
  ) async {
    String fileName = filePath.split('/').last;
    Directory folder =
        Directory('${appDocDir.path}/${filePath.replaceFirst(fileName, '')}');
    await folder.create(recursive: true);
    File file = File('${appDocDir.path}/$filePath');
    return file.writeAsBytes();
  }
}
