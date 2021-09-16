package chapter2.item6;

import java.util.regex.Pattern;

// Reusing expensive object for improved performance (Pages 22 and 23)
public class RomanNumerals {
    // Performance can be greatly improved! (Page 22)
    /*
    Si bien String.matches es la forma más fácil de verificar si una cadena coincide
una expresión regular, no es adecuado para el uso repetido en el rendimiento crítico
situaciones. El problema es que crea internamente una instancia de Pattern para el
expresión regular y la usa solo una vez, después de lo cual se vuelve elegible para
recolección de basura.
     */
    static boolean isRomanNumeralSlow(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})"
                + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    // Reusing expensive object for improved performance (Page 23)
    /*
    Para mejorar el rendimiento, compile explícitamente la expresión regular en un
Instancia de patrón (que es inmutable) como parte de la inicialización de la clase, almacenarla en caché y
reutilice la misma instancia para cada invocación del métod
     */
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD]|D?C{0,3})"
                    + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    static boolean isRomanNumeralFast(String s) {
        return ROMAN.matcher(s).matches();
    }

    public static void main(String[] args) {
        int numSets = Integer.parseInt(args[0]);
        int numReps = Integer.parseInt(args[1]);
        boolean b = false;

        for (int i = 0; i < numSets; i++) {
            long start = System.nanoTime();
            for (int j = 0; j < numReps; j++) {
                b ^= isRomanNumeralSlow("MCMLXXVI");  // Change Slow to Fast to see performance difference
            }
            long end = System.nanoTime();
            System.out.println(((end - start) / (1_000. * numReps)) + " μs.");
        }

        // Prevents VM from optimizing away everything.
        if (!b)
            System.out.println();
    }
}
