export const containsAnyOf = (str: string, chars: string[]): boolean => {
    for (const char of chars) {
        if (str.indexOf(char) !== -1) {
            return true;
        }
    }
    return false;
}


export const ALL_LETTERS = [..."abcdefghijklmnopqrstuvwxyzæøåABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ"];
