/* import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:frontend/core/element_objects/coreField.dart';
import 'package:frontend/utils/dummy_data.dart';

enum CardStatus { like, dislike, skip }

//Class to provide information about the swipe interaction to swipe_card widget
class CardProvider extends ChangeNotifier {
  List<CoreField>? _cards = DummyData.getCoreFields();

  bool _isDragging = false;
  double angle = 0;
  Offset _position = Offset.zero;
  Size _screenSize = Size.zero;

  bool get isDragging => _isDragging;
  Offset get position => _position;
  List<CoreField>? get cards => _cards;

  void startPosition(DragStartDetails details) {
    _isDragging = true;

    notifyListeners();
  }

  //Called continously to give angle and x position to card
  void updatePosition(DragUpdateDetails details) {
    _position += details.delta;
    final x = _position.dx;
    angle = 15 * x / _screenSize.width;
    notifyListeners();
  }

  //Called when dragging action is stoped
  void endPosition() {
    _isDragging = false;
    notifyListeners();

    final status = getStatus();

    if (status != null) {
      switch (status) {
        case CardStatus.like:
          like();

          break;
        case CardStatus.dislike:
          dislike();

          break;

        case CardStatus.skip:
          skip();
          break;
      }
    }

    resetPosition();
  }

  //If Dragging is more than delta pixels, perform like or dislike action
  CardStatus? getStatus() {
    final x = _position.dx;
    final y = _position.dy;
    final delta = 100;

    if (x >= delta) {
      return CardStatus.like;
    } else if (x <= -delta) {
      return CardStatus.dislike;
    } else if (y >= delta) {
      return CardStatus.skip;
    } else {
      return null;
    }
  }

  void resetPosition() {
    _position = Offset.zero;
    notifyListeners();

    angle = 0;
  }

  void like() {
    angle = 20;
    _position += Offset(2 * _screenSize.width, 0);
    _nextCard();

    notifyListeners();
  }

  void dislike() {
    angle = -20;
    _position -= Offset(2 * _screenSize.width, 0);
    _nextCard();

    notifyListeners();
  }

  void skip() {
    angle = 0;
    _position += Offset(0, _screenSize.height);
    _nextCard();

    notifyListeners();
  }

  Future<void> swipeAnimation() async {
    _isDragging = true;

    angle = -20;
    _position = const Offset(50, 0);
    await Future.delayed(const Duration(seconds: 1));
    angle = 20;
    _position = const Offset(-50, 0);
    await Future.delayed(const Duration(seconds: 1));
    angle = 0;
    _position = const Offset(0, 0);
    _isDragging = false;
    print("animation done");
  }

  Future _nextCard() async {
    if (_cards!.isEmpty) return;

    await Future.delayed(Duration(milliseconds: 200));
    _cards!.removeLast();
    if (_cards!.isEmpty) {
      resetCards();
    }
    resetPosition();
  }

  void setScreenSize(Size size) {
    this._screenSize = size;
  }

  void resetCards() {
    _cards = DummyData.getCoreFields().reversed.toList();

    notifyListeners();
  }
}
 */