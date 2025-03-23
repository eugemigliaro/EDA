package ej10;

import java.util.HashMap;

public class QGram {
    /*Ejercicio 10
10.1) Escribir la clase Java QGram que calcule los Q-grams para Q exactamente N y N
es un parámetro del constructor. Dicha clase deberá además tener dos métodos de
instancia:
● printTokens(String) para obtener los pares Qgram y cantidad de apariciones
● similarity(String, String) que calcula la similitud normalizada entre ambos
strings.
Realizar testeos de unidad para verificar correctitud.
Caso de Uso:
QGram g= new Qgram(2); // 1, 2, 3 .etc
g.printTokens(“alal”); // no importa el orden del output
// #a 1
// al 2
// la 1
// l# 1
…
double value= g.similarity("salesal“, "alale“);
System.out.println(value);*/

    private final int Q;
    public QGram(int q){
        this.Q = q;
    }

    public HashMap<String, Integer> tokenize(String s) {
        char[] chars = new char[s.length() + (Q - 1) * 2];
        for(int i = 0; i < Q - 1; i++) {
            chars[i] = '#';
            chars[chars.length - 1 - i] = '#';
        }
        for(int i = Q - 1; i < s.length() + Q - 1; i++) {
            chars[i] = s.charAt(i - (Q - 1));
        }

        //los repetidos no los tengo que guardar dos veces, puedo devolver directo un hashmap con los tokens y su cantidad de apariciones
        HashMap<String, Integer> tokens = new HashMap<>();

        for(int i = 0; i < s.length() + Q - 1; i++) {
            String token = new String(chars, i, Q);
            if(tokens.containsKey(token)) {
                tokens.put(token, tokens.get(token) + 1);
            } else {
                tokens.put(token, 1);
            }
        }

        return tokens;
    }

    public double similarity(String s1, String s2) {
        HashMap<String, Integer> tokens1 = tokenize(s1);
        HashMap<String, Integer> tokens2 = tokenize(s2);
        int intersection = 0;
        for(String token : tokens1.keySet()) {
            if(tokens2.containsKey(token)) {
                intersection += Math.min(tokens1.get(token), tokens2.get(token));
            }
        }
        return (double) intersection / (Math.max(s1.length(), s2.length()) == 0 ? 1 : Math.max(s1.length(), s2.length()));
    }
}
