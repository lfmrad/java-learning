/* Luis Fernando Martínez Andreu
 * Actividad Semana 17 - Parte 3
 */

import java.util.concurrent.ThreadLocalRandom;
import java.text.DecimalFormat;

public class TarjetaRegalo {
    double saldo;
    int numTarjeta;
    DecimalFormat df = new DecimalFormat("0.00 €");

    public static void main(String[] args) {
        TarjetaRegalo t1 = new TarjetaRegalo(100); 
        TarjetaRegalo t2 = new TarjetaRegalo(120); 

        t1.gasta(45.90);
        t2.gasta(5);
        t2.gasta(200);
        t1.gasta(3.55);

        System.out.println(t1.comprobarSaldo());
        System.out.println(t2.comprobarSaldo());

        TarjetaRegalo t3 = t1.fusionaCon(t2);
        
        System.out.println(t1.comprobarSaldo());
        System.out.println(t2.comprobarSaldo());
        System.out.println(t3.comprobarSaldo());
    }

    TarjetaRegalo (double saldoInicial) {
        this.saldo = saldoInicial;
        this.numTarjeta = getRandomCardNumber();
    }

    void gasta(double saldoGastado) {
        if (this.saldo - saldoGastado < 0) {
            System.out.println("Insuficiente saldo disponible para gastar " + this.df.format(saldoGastado));
        } else {
            this.saldo -= saldoGastado;
        }
    }

    String comprobarSaldo() {
        return this.df.format(this.saldo);
    }

    int comprobarNumero() {
        return this.numTarjeta;
    }

    TarjetaRegalo fusionaCon(TarjetaRegalo tarjetaDestino) {
            TarjetaRegalo nuevaTarjeta = new TarjetaRegalo(this.saldo + tarjetaDestino.saldo);
            this.saldo = tarjetaDestino.saldo = 0;
            return nuevaTarjeta; 
    }

    private int getRandomCardNumber() {
        // Genera números de tarjeta entre el 10000 y el 99999 para evitar números de 5 digitos que comiencen por 0.
        return ThreadLocalRandom.current().nextInt(10000, 99999 + 1);
    }
}