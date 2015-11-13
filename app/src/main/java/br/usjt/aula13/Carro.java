package br.usjt.aula13;

import java.io.Serializable;


public class Carro implements Comparable<Carro>,Serializable{

    private String marca;
    private String fabricante;
    private double preco;
    private double precoCon;
    private String imagem;
    private String estado;

    public static final String NAO_ENCONTRADA="Não Encontrada";


    public Carro(String marca,String fabricante,double preco,double precoCon,String imagem,String estado) {
        this.marca=marca;
        this.fabricante=fabricante;
        this.preco=preco;
        this.precoCon=precoCon;
        this.imagem=imagem;
        this.estado=estado;
    }

    public String getMarca(){return marca;}

    public String getFabricante(){return fabricante;}

    public double getPreco(){return preco;}

    public double getPrecoCon() {return precoCon;}

    public String getImagem(){return imagem;}

    public String getEstado(){return estado;}

    @Override
    public String toString(){
        return ".br.usjt.aula13.Carro{"+
                "marca='" + marca + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", preco='" + preco + '\'' +
                ", precoCon='" + precoCon + '\'' +
                ", imagem='" + imagem + '\'' +
                ", estado='" + estado + '\'' +

                '}';

    }

    @Override
    public int compareTo(Carro carro) {
        if (marca.equals(carro.getMarca())
                && estado.equals(carro.getEstado())
                ) {
            return 0;
        }
        return this.getMarca().compareTo(carro.getMarca());
    }

}
