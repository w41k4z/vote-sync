import 'package:equatable/equatable.dart';

abstract class Failure extends Equatable {
  final List? properties;

  const Failure({this.properties});

  @override
  List get props => [properties];
}
