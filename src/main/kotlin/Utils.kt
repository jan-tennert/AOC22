fun List<String>.filterEmptyLines(): List<String> {
    return this.filter { it.isNotBlank() }
}