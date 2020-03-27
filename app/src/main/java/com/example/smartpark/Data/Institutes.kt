package com.example.smartpark.Data

class Institutes private constructor(){
    companion object {
        fun getInstitutesList() : List<String> = listOf(
            "CECOM - Centro de Saúde da Comunidade",
            "FCM - Faculdade de Ciências Médicas",
            "FE - Faculdade de Educação",
            "FEA - Faculdade de Engenharia de Alimentos",
            "FEAGRI - Faculdade de Engenharia Agrícola",
            "FEC - Faculdade de Engenharia Civil",
            "FEEC - Faculdade de Eng. Elétrica e Comp.",
            "FEF - Faculdade de Educação Física",
            "FEM - Faculdade de Egenharia Mecânica",
            "FENF - Faculdade de Enfermagem",
            "FEQ - Faculdade de Engenharia Química",
            "IA - Instituto de Artes",
            "IB - Instituto de Biologia",
            "IC - Instituto de Computação",
            "IE - Instituto de Economia",
            "IEL - Instituto de Estudos da Linguagem",
            "IFCH - Instituto de Filosofia e Ciências Humanas",
            "IFGW - Instituto de Física Gleb Wataghin",
            "IG - Instituto de Geociências",
            "IMECC - Instituto de Matemática, Est. e Comp.",
            "IQ - Instituto de Química"
            )
    }
}