class Pageable {
  int totalItems;
  int itemsPerPage;
  int page;
  String sorting;

  Pageable(
      {required this.totalItems,
      required this.itemsPerPage,
      required this.page,
      required this.sorting});

  factory Pageable.fromJson(Map<String, dynamic> json) {
    return Pageable(
      totalItems: json['totalItems'],
      itemsPerPage: json['itemsPerPage'],
      page: json['page'],
      sorting: json['sorting'],
    );
  }
}
