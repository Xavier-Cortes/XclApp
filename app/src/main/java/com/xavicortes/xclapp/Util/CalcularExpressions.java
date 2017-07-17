package com.xavicortes.xclapp.Util;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Character;

// Permet calcular expresions
// tots els mètodes son estatics per facilitar-ne el ús de la classe
public class CalcularExpressions {
    static String sAns = "ans";

    // Calcula el resultat de una expressió matematica de addicions i productes
    // te en compte la prioritat de les operacions
    // la lògica de la rutina es fer servir patrons tipus Valor Operació Valor tenint en compte que els valors
    // poden tenir o no el signe al davant,
    // es pot ampliar facilment:
    //      Buscant patrons entre parèntesi i després aplicar recursivitat
    //      Buscar patrons de potència "^" abans que els de producte
    //      Buscant operacions unitaris de funcions científiques de la forma  op(valor) Op = sin, cos, tan etc...
    // millorar el lio amb els signes per tal de no tenir que reduir els signes després de cada substitució ara es obligat
    // perque en el cas de que començi una expressió amb el signe negatiu cal quedi adjuntat al numbre si no el resultat seria
    // incorrecte cas: -1+2x3 cal que el signe
    public static Double CalcularExpressio(String s, double ans) {
        double resultat = 0.0;
        String PattenNumeroDoble  =  "(?:[\\+-]?\\d+(?:\\.\\d+)?)";
        String PattenNumeroDobleSenseSigne  =  "(?:\\d+(?:\\.\\d+)?)";


        // Patró per buscar totes les ocurrencies de signes
        Pattern PatroSignes= Pattern.compile("(\\+-|-\\+|--|\\+\\+)");

        // Busca totes les ocurrencies de signes
        // per mantenir la priotritat de les operacions càlcula primer totes les operacions de unitaries
        // i substitueix la cadena d'expresio amb el signe resultatnt
        while (true){
            Matcher m = PatroSignes.matcher(s);
            // busca ocurrencies
            // calcula
            // substitueix
            // torna a intentar
            if (m.find()) {
                String res = "";
                MatchResult mr = m.toMatchResult();


                switch (mr.group(1).toString()) {
                    case "++":
                    case "--":
                        res = "+";
                        break;

                    case "+-":
                    case "-+":
                        res = "-";
                        break;
                }
                // substitueix el valor en la cadena a analitzar
                s = s.substring(0, m.start()) + res + s.substring(m.end());

            } else {
                break;
            }

        }

        // Patró per buscar totes les ocurrencies de producte
        Pattern PatroProducte = Pattern.compile("(" + PattenNumeroDoble + "|ans)(x|/|%)(" + PattenNumeroDoble + "|ans)");

        // Busca totes les ocurrencies de producte
        // per mantenir la priotritat de les opercions càlcula primer totes les operacions de producte
        // i substitueix la cadena d'expresio amb el resultat
        while (true){
            // busca ocurrencies
            // calcula
            // substitueix a la cedena d'entrada
            // torna a intentar
            Matcher m = PatroProducte.matcher(s);
            if (m.find()) {
                Double n1 = 0.0;
                Double n2 = 0.0;
                String op = "";
                Double res = 0.0;
                MatchResult mr = m.toMatchResult();

                n1 =SubstituirAns(mr.group(1).toString(),ans );
                op = mr.group(2).toString();
                n2 =SubstituirAns(mr.group(3).toString(),ans );
                switch (op) {
                    case "x":
                        res = Multiplica(n1, n2);
                        break;

                    case "/":
                        res = Dividir(n1, n2);
                        break;

                    case "%":
                        res = Percent(n1, n2);
                        break;

                }
                // substitueix el valor dins la cadena a analitzar afegeix  un signe positiu per evitar que es quedi sense operació
                s = s.substring(0, m.start()) + "+" +  res.toString() + s.substring(m.end());

            } else {
                break;
            }

        }


        // Busca totes les ocurrencies de signes agrupats i els redueix a un únic signe
        while (true){
            Matcher m = PatroSignes.matcher(s);
            // busca ocurrencies
            // calcula
            // substitueix
            // torna a intentar
            if (m.find()) {
                String res = "";
                MatchResult mr = m.toMatchResult();


                switch (mr.group(1).toString()) {
                    case "++":
                    case "--":
                        res = "+";
                        break;

                    case "+-":
                    case "-+":
                        res = "-";
                        break;
                }
                // substitueix el valor en la cadena a analitzar
                s = s.substring(0, m.start()) + res + s.substring(m.end());

            } else {
                break;
            }

        }

        // Patró per buscar totes les ocurrencies de addicio
        Pattern PatroAddicio = Pattern.compile("(" + PattenNumeroDoble + "|ans)(\\+|-)(" + PattenNumeroDoble + "|ans)");

        // Busca totes les ocurrencies de producte
        // per mantenir la priotritat de les opercions calcula primer totes les operacions de producte
        // i substitueix la cadena d'expresio amb el
        while (true){
            Matcher m = PatroAddicio.matcher(s);
            // busca ocurrencies
            // calcula
            // substitueix a la cedena d'entrada
            // torna a intentar
            if (m.find()) {
                Double n1 = 0.0;
                Double n2 = 0.0;
                String op = "";
                Double res = 0.0;
                MatchResult mr = m.toMatchResult();

                n1 =SubstituirAns(mr.group(1).toString(),ans );
                op = mr.group(2).toString();
                n2 =SubstituirAns(mr.group(3).toString(),ans );
                switch (op) {
                    case "+":
                        res = Suma(n1, n2);
                        break;

                    case "-":
                        res = Resta(n1, n2);
                        break;

                }
                // substitueix el valor dins la cadena a analitzar
                s = s.substring(0, m.start()) + "+" + res.toString() + s.substring(m.end());

            } else {
                break;
            }

        }

        // Busca totes les ocurrencies de signes
        while (true){
            Matcher m = PatroSignes.matcher(s);
            // busca ocurrencies
            // calcula
            // substitueix
            // torna a intentar
            if (m.find()) {
                String res = "";
                MatchResult mr = m.toMatchResult();


                switch (mr.group(1).toString()) {
                    case "++":
                    case "--":
                        res = "+";
                        break;

                    case "+-":
                    case "-+":
                        res = "-";
                        break;
                }
                // substitueix el valor en la cadena a analitzar
                s = s.substring(0, m.start()) + res + s.substring(m.end());

            } else {
                break;
            }

        }
        // Retorna el resultat de la última operació de substitució
        // també la expresió podria ser "ans" i per tant s'ha de fer la substitució
        return Double.valueOf(SubstituirAns(s,ans));
    }

    // Realitza la substitucio en el cas que s'hagi definit la variable ans
    private static double SubstituirAns(String expr, double ans) {
    if ( expr.equals(sAns)  ) return ans ; else return Double.valueOf(expr);
    }


    // Operacions bàsiques
    public static  double Suma(double a, double b) {
        return a+b;
    }

    public static  double Resta(double a, double b) {
        return a-b;
    }

    public static  double Multiplica(double a, double b) {
        return a*b;
    }

    public static  double Dividir(double a, double b) {
        return a/b;
    }

    public static  double Percent(double a, double b) {
        return a*b/100;
    }

    public static  double Signe(double a) {
        return a*-1;
    }

    
}
