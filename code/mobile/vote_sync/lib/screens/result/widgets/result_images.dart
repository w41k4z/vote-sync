import 'dart:io';

import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/models/polling_station_result_image.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/widgets/full_screen_image_viewer.dart';

class ResultImages extends StatelessWidget {
  final List<PollingStationResultImage> images;

  const ResultImages({super.key, required this.images});

  @override
  Widget build(BuildContext context) {
    final LocalStorageService localStorageService =
        GetIt.I.get<LocalStorageService>();
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: images.isNotEmpty
          ? _carousel(context, localStorageService)
          : const Center(
              child: Text(
                'Aucun résultat trouvé',
                style: TextStyle(
                  color: AppColors.redDanger,
                ),
              ),
            ),
    );
  }

  Widget _carousel(
      BuildContext context, LocalStorageService localStorageService) {
    return CarouselSlider(
      options: CarouselOptions(),
      items: images
          .map(
            (item) => GestureDetector(
              onTap: () => _openFullScreen(
                context,
                images.indexOf(item),
                localStorageService,
              ),
              child: Container(
                margin: const EdgeInsets.all(5.0),
                child: ClipRRect(
                  borderRadius: const BorderRadius.all(Radius.circular(5.0)),
                  child: Stack(
                    children: <Widget>[
                      Image.file(
                        File(
                            '${localStorageService.appDocDir.path}/${item.imagePath}'),
                        fit: BoxFit.cover,
                        width: 1000.0,
                      ),
                      Positioned(
                        bottom: 0.0,
                        left: 0.0,
                        right: 0.0,
                        child: Container(
                          decoration: const BoxDecoration(
                            gradient: LinearGradient(
                              colors: [
                                Color.fromARGB(200, 0, 0, 0),
                                Color.fromARGB(0, 0, 0, 0)
                              ],
                              begin: Alignment.bottomCenter,
                              end: Alignment.topCenter,
                            ),
                          ),
                          padding: const EdgeInsets.symmetric(
                            vertical: 10.0,
                            horizontal: 20.0,
                          ),
                          child: Text(
                            'Image ${images.indexOf(item) + 1}',
                            style: const TextStyle(
                              color: Colors.white,
                              fontSize: 20.0,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          )
          .toList(),
    );
  }

  void _openFullScreen(
    BuildContext context,
    int initialIndex,
    LocalStorageService localStorageService,
  ) {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => FullScreenImageViewer(
          imageList: images
              .map((item) =>
                  '${localStorageService.appDocDir.path}/${item.imagePath}')
              .toList(),
          initialIndex: initialIndex,
        ),
      ),
    );
  }
}
