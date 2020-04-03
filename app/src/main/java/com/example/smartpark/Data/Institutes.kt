package com.example.smartpark.Data

class Institutes private constructor(){
    companion object {
        fun getInstitutesList() : List<Institute> = listOf(
            Institute("CECOM - Centro de Saúde da Comunidade", -22.827897, -47.064050),
            Institute("FCM - Faculdade de Ciências Médicas", -22.829864, -47.063176),
            Institute("FE - Faculdade de Educação", -22.816552, -47.065786),
            Institute("FEA - Faculdade de Engenharia de Alimentos", -22.820162, -47.067281),
            Institute("FEAGRI - Faculdade de Engenharia Agrícola", -22.819103, -47.060862),
            Institute("FEC - Faculdade de Engenharia Civil", -22.816386, -47.062050),
            Institute("FEEC - Faculdade de Eng. Elétrica e Comp.", -22.820907, -47.066363),
            Institute("FEF - Faculdade de Educação Física", -22.815121, -47.072646),
            Institute("FEM - Faculdade de Egenharia Mecânica", -22.819458, -47.065951),
            Institute("FENF - Faculdade de Enfermagem", -22.830861, -47.063238),
            Institute("FEQ - Faculdade de Engenharia Química", -22.821104, -47.064465),
            Institute("IA - Instituto de Artes", -22.815354, -47.064491),
            Institute("IB - Instituto de Biologia", -22.818584, -47.069869),
            Institute("IC - Instituto de Computação", -22.814772, -47.064560),
            Institute("IE - Instituto de Economia", -22.814785, -47.065510),
            Institute("IEL - Instituto de Estudos da Linguagem", -22.815167, -47.069351),
            Institute("IFCH - Instituto de Filosofia e Ciências Humanas", -22.814970, -47.067984),
            Institute("IFGW - Instituto de Física Gleb Wataghin", -22.817561, -47.067180),
            Institute("IG - Instituto de Geociências", -22.813279, -47.069016),
            Institute("IMECC - Instituto de Matemática, Est. e Comp.", -22.815771, -47.067642),
            Institute("IQ - Instituto de Química", -22.819143, -47.067883)
        )

        fun getInstituteNamesList() : MutableList<String> {
            var listInstitutesNames : MutableList<String> = mutableListOf<String>()
            this.getInstitutesList().forEach { listInstitutesNames.add(it.instituteName) }
            return listInstitutesNames
        }
    }
}

//           Institute(
//                "CECOM - Centro de Saúde da Comunidade",
//                -22.827897,
//                -47.064050,
//                "150",
//                "Vital Brasil",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-881"
//            ),
//            Institute(
//                "FCM - Faculdade de Ciências Médicas",
//                -22.829864,
//                -47.063176,
//                "90",
//                "Albert Sabin",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-894"
//            ),
//            Institute(
//                "FE - Faculdade de Educação",
//                -22.816552,
//                -47.065786,
//                "801",
//                "Bertrand Russell",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-865"
//            ),
//            Institute(
//                "FEA - Faculdade de Engenharia de Alimentos",
//                -22.820162,
//                -47.067281,
//                "80",
//                "Monteiro Lobato",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-862"
//            ),
//            Institute(
//                "FEAGRI - Faculdade de Engenharia Agrícola",
//                -22.819103,
//                -47.060862,
//                "501",
//                "Candido Rondon",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-875"
//            ),
//            Institute(
//                "FEC - Faculdade de Engenharia Civil",
//                -22.816386,
//                -47.062050,
//                "224",
//                "Saturnino de Brito",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-889"
//            ),
//            Institute(
//                "FEEC - Faculdade de Eng. Elétrica e Comp.",
//                -22.820907,
//                -47.066363,
//                "400",
//                "Albert Einstein",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-852"
//            ),
//            Institute(
//                "FEF - Faculdade de Educação Física",
//                -22.815121,
//                -47.072646,
//                "701",
//                "Erico Verissimo",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-851"
//            ),
//            Institute(
//                "FEM - Faculdade de Egenharia Mecânica",
//                -22.819458,
//                -47.065951,
//                "200",
//                "Rua Mendeleyev",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-860"
//            ),
//            Institute(
//                "FENF - Faculdade de Enfermagem",
//                -22.830861,
//                -47.063238,
//                "126",
//                "Tessalia Vieira de Camargo",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-887"
//            ),
//            Institute(
//                "FEQ - Faculdade de Engenharia Química",
//                -22.821104,
//                -47.064465,
//                "500",
//                "Albert Einstein",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-852"
//            ),
//            Institute(
//                "IA - Instituto de Artes",
//                -22.815354,
//                -47.064491,
//                "50",
//                "Elis Regina",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-854"
//            ),
//            Institute(
//                "IB - Instituto de Biologia",
//                -22.818584,
//                -47.069869,
//                "255",
//                "Monteiro Lobato",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-862"
//            ),
//            Institute(
//                "IC - Instituto de Computação",
//                -22.814772,
//                -47.064560,
//                "1251",
//                "Albert Einstein",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-852"
//            ),
//            Institute(
//                "IE - Instituto de Economia",
//                -22.814785,
//                -47.065510,
//                "353",
//                "Rua Pitagoras",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-857"
//            ),
//            Institute(
//                "IEL - Instituto de Estudos da Linguagem",
//                -22.815167,
//                -47.069351,
//                "511",
//                "Sergio Buarque de Holanda",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-859"
//            ),
//            Institute(
//                "IFCH - Instituto de Filosofia e Ciências Humanas",
//                -22.814970,
//                -47.067984,
//                "100",
//                "Cora Coralina",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-896"
//            ),
//            Institute(
//                "IFGW - Instituto de Física Gleb Wataghin",
//                -22.817561,
//                -47.067180,
//                "777",
//                "Sergio Buarque de Holanda",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-859"
//            ),
//            Institute(
//                "IG - Instituto de Geociências",
//                -22.813279,
//                -47.069016,
//                "250",
//                "Carlos Gomes",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-855"
//            ),
//            Institute(
//                "IMECC - Instituto de Matemática, Est. e Comp.",
//                -22.815771,
//                -47.067642,
//                "651",
//                "Sergio Buarque de Holanda",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-859"
//            ),
//            Institute(
//                "IQ - Instituto de Química",
//                -22.819143,
//                -47.067883,
//                "126",
//                "Josue de Castro",
//                "Campinas",
//                "Barao Geraldo",
//                "Brazil",
//                "13083-861"
//            )