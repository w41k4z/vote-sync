export class NameFormat {
  static format(value: string): unknown {
    const romanNumerals = ['I', 'II', 'III', 'IV', 'V', 'VI'];
    // Convert the string to lowercase, then split it by space to work on each word
    return value
      .toLowerCase()
      .split(' ')
      .map((word, _) => {
        if (romanNumerals.includes(word.toUpperCase())) {
          return word.toUpperCase();
        }

        return word.charAt(0).toUpperCase() + word.slice(1);
      })
      .join(' ');
  }
}
