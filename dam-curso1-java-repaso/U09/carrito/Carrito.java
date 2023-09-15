import java.util.ArrayList;

public class Carrito {
    private ArrayList<Elemento> añadidos = new ArrayList<>();

    public void agrega(Elemento elemento) {
        this.añadidos.add(elemento);
    }

    public int numeroDeElementos() {
        return this.añadidos.size();
    }

    public double importeTotal() {
        double total = 0;
        for (Elemento elemento : añadidos) {
            total += elemento.getPrice() * elemento.getUnits();
        }
        return total;
    }

    @Override
    public String toString() {
        String info;
        info = "\nContenido del carrito\n";
        info += "=====================\n";
        for (Elemento elemento : añadidos) {
            info += elemento.getName();
            info += " PVP: " + String.format("%.2f", elemento.getPrice()); 
            info += " Unidades: " + elemento.getUnits(); 
            info += " Subtotal: " + String.format("%.2f", elemento.getPrice() * elemento.getUnits()) +"\n"; 
        }
        return info;
    }
}

/* MENSAJE DE REFERENCIA PARA IMPLEMENTAR toString

Contenido del carrito
=====================
Tarjeta SD 64Gb PVP: 19,95 Unidades: 2 Subtotal: 39,90
Canon EOS 2000D PVP: 449,00 Unidades: 1 Subtotal: 449,00
Samsung Galaxy Tab PVP: 199,00 Unidades: 3 Subtotal: 597,00
Tarjeta SD 64Gb PVP: 19,95 Unidades: 1 Subtotal: 19,95 */