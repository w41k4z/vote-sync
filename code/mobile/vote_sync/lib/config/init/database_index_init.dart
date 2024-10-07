class DatabaseIndexInit {
  static const String CREATE_VOTERS_NAME_IDX =
      "CREATE INDEX idx_voters_name ON voters(name)";

  static const String CREATE_VOTERS_FIRST_NAME_IDX =
      "CREATE INDEX idx_voters_first_name ON voters(first_name)";

  static const String CREATE_VOTERS_HAS_VOTE_IDX =
      "CREATE INDEX idx_voters_has_voted ON voters(has_voted)";
}
