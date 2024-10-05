class DatabaseIndexInit {
  static const String CREATE_ELECTORS_NAME_IDX =
      "CREATE INDEX idx_electors_name ON electors(name)";

  static const String CREATE_ELECTORS_FIRST_NAME_IDX =
      "CREATE INDEX idx_electors_first_name ON electors(first_name)";

  static const String CREATE_ELECTORS_HAS_VOTE_IDX =
      "CREATE INDEX idx_electors_has_voted ON electors(has_voted)";
}
