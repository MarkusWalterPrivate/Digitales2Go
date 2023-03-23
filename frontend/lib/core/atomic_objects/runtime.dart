class Runtime {
  int id;
  int start;
  int finish;
  bool useOnlyYear;

  Runtime(
      {required this.id,
      required this.start,
      required this.finish,
      required this.useOnlyYear});

  factory Runtime.fromJson(Map<String, dynamic> json) {
    return Runtime(
      id: json['id'] ?? 0,
      start: json['start'],
      finish: json['finished'],
      useOnlyYear: json['useOnlyYear'],
    );
  }
}
