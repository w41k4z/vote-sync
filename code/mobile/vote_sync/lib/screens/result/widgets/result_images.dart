import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class ResultImages extends StatelessWidget {
  final List<String> imgList = const [
    'https://imgs.search.brave.com/OgGaEx-Gyk8w9aTSqxxpfiZP5jm119CFJSzv2v5PUUA/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9zMy5h/bWF6b25hd3MuY29t/L3ZpcmdpbmlhLndl/YnJhbmQuY29tL3Zp/cmdpbmlhL21HeUxy/aVVScm5uLzk1ODlj/MmM5NmUyMDI0YTlj/MDYxMjI0Nzg3N2Yy/ZGE0LzM0NC8xNjk0/NjM0MjMxLnBuZw',
    'https://imgs.search.brave.com/O4xGrC_EcPpOGWQAxPE4zA4Psy__-wL4yRXRzsqw7-c/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9kMm1l/MTJ5bzhycjBvMC5j/bG91ZGZyb250Lm5l/dC93cC1jb250ZW50/L3VwbG9hZHMvd2hh/dC1zaXplLWlzLWE0/LmpwZw',
    'https://imgs.search.brave.com/wiOnp-fWCkQZdTawdVXYn3iOutFZjHF3yGtriQPJKu8/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9zMy5h/bWF6b25hd3MuY29t/L3ZpcmdpbmlhLndl/YnJhbmQuY29tL3Zp/cmdpbmlhL3RTQko3/QVU2RmQzLzY3MWY0/OTU4YTc4ZTVlMzA2/MGEyN2NmMWNjZjhi/YjIxLzM0NC8xNjk0/NjMzMTYwLnBuZw',
  ];

  const ResultImages({super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: imgList.isNotEmpty
          ? _carousel()
          : const Center(
              child: Text(
                'Aucune image',
                style: TextStyle(
                  color: AppColors.redDanger,
                ),
              ),
            ),
    );
  }

  Widget _carousel() {
    return CarouselSlider(
      options: CarouselOptions(),
      items: imgList
          .map(
            (item) => Container(
              margin: const EdgeInsets.all(5.0),
              child: ClipRRect(
                borderRadius: const BorderRadius.all(Radius.circular(5.0)),
                child: Stack(
                  children: <Widget>[
                    Image.network(item, fit: BoxFit.cover, width: 1000.0),
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
                          'No. ${imgList.indexOf(item)} image description',
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
          )
          .toList(),
    );
  }
}
