package com.xavicortes.xclapp;

public class Operacio {

    private double num = 0 ;
    private Operacio OpAnt = null; 
    private OperacioEnum Ope = null;

    public enum PrioritatOpEnum {
        Adició
        ,Producte
        ,Potencia
        ,Unitari
        ,Resultat;
    }

    public enum OperacioEnum {
        Suma(R.id.btMas, "+", Operacio.PrioritatOpEnum.Adició)
        , Resta(R.id.btMenos, "-", Operacio.PrioritatOpEnum.Adició)
        , Multiplicacio(R.id.btMult, "x" , Operacio.PrioritatOpEnum.Producte)
        , Divisio(R.id.btDiv, "/", Operacio.PrioritatOpEnum.Producte)
        , Percentatge(R.id.btPorCent, "%", Operacio.PrioritatOpEnum.Producte)
        , Signe(R.id.btSign, "+-", Operacio.PrioritatOpEnum.Unitari)
        , Igual(R.id.btIgual, "=", Operacio.PrioritatOpEnum.Resultat);

        int id = 0;
        String OpStr = "";
        public Operacio.PrioritatOpEnum Prioritat = Operacio.PrioritatOpEnum.Resultat;

        OperacioEnum(int btnid, String s, Operacio.PrioritatOpEnum Prioritat) {
            this.id = btnid;
            this.OpStr = s;
            this.Prioritat = Prioritat;
        }

        public int getId() {
            return id;
        }

        public String getOpStr() {
            return OpStr;
        }

        public PrioritatOpEnum getPrioritat() {
            return Prioritat;
        }

        public static OperacioEnum BuscarID(int id) {
            for (OperacioEnum ope : values()) {
                if (ope.id == id) return ope;
            }
            return null;
        }

        public static OperacioEnum BuscarNivell(Operacio.PrioritatOpEnum Prioritat) {
            for (OperacioEnum ope : values()) {
                if (ope.Prioritat == Prioritat) return ope;
            }
            return null;
        }

    }

    public Operacio(double num, int id, Operacio OpeAnt) {
        this.num = num;
        this.Ope = OperacioEnum.BuscarID(id);
        this.OpAnt = OpeAnt;
    }


    public void setNum(double num) {
       this.num = num;
    }

    public double getNum() {
        return num;
    }

    public void setOpAnt(Operacio Op) {
       this.OpAnt= Op;
   }

    public Operacio getOpAnt() {
        return OpAnt;
   }

    public void setOpe(OperacioEnum ope) {
       this.Ope = Ope;
   }

    public OperacioEnum getOpe() {
        return Ope;
    }

    public double CalcularOp() throws Exception {

         while (OpAnt != null) {

             if (this.Ope.Prioritat == OpAnt.Ope.Prioritat || this.Ope.Prioritat  == PrioritatOpEnum.Resultat ) {

                 num = ExecutarOperacio(OpAnt.num, num, OpAnt.Ope);
                 OpAnt = OpAnt.OpAnt;

             } else if (this.Ope.Prioritat  == PrioritatOpEnum.Unitari) {
                 num = ExecutarOperacio(OpAnt.num, 0, OpAnt.Ope);
                 OpAnt = OpAnt.OpAnt;

             } else {
                 break;

             }



         }
        return num;
    }

    public  double ExecutarOperacio(double a, double b, OperacioEnum Op) throws Exception {

        if ( Op.getOpStr()== "+") {
            return Suma(a,b);

        } else if ( Op.getOpStr() == "-") {
            return Resta(a,b);

        } else if ( Op.getOpStr() == "x") {
            return Multiplica(a,b);

        } else if ( Op.getOpStr() == "/") {
            return Dividir(a,b);

        } else if ( Op.getOpStr() == "%") {
            return Percent(a,b);

        } else if ( Op.getOpStr() == "+-") {
            return Signe(a);

        } else {
            throw new Exception("Operació incorrecte");
        }
    }
    
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

    public static double Igual() {
        //TODO fer la funcio igual
        return 0;
    }
        
    
    
}
