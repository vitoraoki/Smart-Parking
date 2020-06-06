package com.example.smartparking.Data

import com.example.smartparking.Models.Institute

class Institutes private constructor(){
    companion object {
        fun getInstitutesList() : List<Institute> {
            val institutes = listOf(
                Institute(
                    "0",
                    "CECOM - Centro de Saúde da Comunidade",
                    -22.827897,
                    -47.064050,
                    "Vital Brasil",
                    "150",
                    "13083-881",
                    "c7078417-61c6-49dd-b34c-36ce7fa3615f"
                ),
                Institute(
                    "1",
                    "FCM - Faculdade de Ciências Médicas",
                    -22.829864,
                    -47.063176,
                    "Albert Sabin",
                    "90",
                    "13083-894",
                    "788ce59b-bc6e-4a0b-8854-75b0c83fdac6"
                ),
                Institute(
                    "2",
                    "FE - Faculdade de Educação",
                    -22.816552,
                    -47.065786,
                    "Bertrand Russell",
                    "801",
                    "13083-865",
                    "85981c33-4233-49fd-ba91-78419433b1eb"
                ),
                Institute(
                    "3",
                    "FEA - Faculdade de Engenharia de Alimentos",
                    -22.820162,
                    -47.067281,
                    "Monteiro Lobato",
                    "80",
                    "13083-862",
                    "d56706ec-7725-4321-908a-83dca85f5b21"
                ),
                Institute(
                    "4",
                    "FEAGRI - Faculdade de Engenharia Agrícola",
                    -22.819103,
                    -47.060862,
                    "Candido Rondon",
                    "501",
                    "13083-875",
                    "99841f5e-91b5-4bb2-9231-e779cd9f3c0a"
                ),
                Institute(
                    "5",
                    "FEC - Faculdade de Engenharia Civil",
                    -22.816386,
                    -47.062050,
                    "Saturnino de Brito",
                    "224",
                    "13083-889",
                    "582d2b89-98d7-43dc-9270-3987b9b0672c"
                ),
                Institute(
                    "6",
                    "FEEC - Faculdade de Eng. Elétrica e Comp.",
                    -22.820907,
                    -47.066363,
                    "Albert Einstein",
                    "400",
                    "13083-852",
                    "951fba32-9435-4174-9481-5dc452a3530e"
                ),
                Institute(
                    "7",
                    "FEF - Faculdade de Educação Física",
                    -22.815121,
                    -47.072646,
                    "Erico Verissimo",
                    "701",
                    "13083-851",
                    "23c05372-93e9-45f0-a24d-234c4a79cb4e"
                ),
                Institute(
                    "8",
                    "FEM - Faculdade de Egenharia Mecânica",
                    -22.819458,
                    -47.065951,
                    "Rua Mendeleyev",
                    "200",
                    "13083-860",
                    "c866c53b-887b-44d4-8e3f-837fcc5102e9"
                ),
                Institute(
                    "9",
                    "FENF - Faculdade de Enfermagem",
                    -22.830861,
                    -47.063238,
                    "Tessalia Vieira de Camargo",
                    "126",
                    "13083-887",
                    "2d7e9e18-7783-4fcf-b21f-27bb2b97d201"
                ),
                Institute(
                    "10",
                    "FEQ - Faculdade de Engenharia Química",
                    -22.821104,
                    -47.064465,
                    "Albert Einstein",
                    "500",
                    "13083-852",
                    "252b601d-42d4-41c0-9d9e-8bf120ad9fcd"
                ),
                Institute(
                    "11",
                    "IA - Instituto de Artes",
                    -22.815354,
                    -47.064491,
                    "Elis Regina",
                    "50",
                    "13083-854",
                    "24aaae89-353b-4352-8986-1482e968a3a8"
                ),
                Institute(
                    "12",
                    "IB - Instituto de Biologia",
                    -22.818584,
                    -47.069869,
                    "Monteiro Lobato",
                    "255",
                    "13083-862",
                    "74d556d5-319e-4089-80b7-31b749653326"
                ),
                Institute(
                    "13",
                    "IC - Instituto de Computação",
                    -22.814772,
                    -47.064560,
                    "Albert Einstein",
                    "1251",
                    "13083-852",
                    "996f7ee9-f90a-46dd-a81e-f451e711b84a"
                ),
                Institute(
                    "14",
                    "IE - Instituto de Economia",
                    -22.814785,
                    -47.065510,
                    "Rua Pitagoras",
                    "353",
                    "13083-857",
                    "8e308f97-db7d-4226-a635-96e5c84bf494"
                ),
                Institute(
                    "15",
                    "IEL - Instituto de Estudos da Linguagem",
                    -22.815167,
                    -47.069351,
                    "Sergio Buarque de Holanda",
                    "511",
                    "13083-859",
                    "809908a7-fc39-498a-8e01-08b3f1ef8bf2"
                ),
                Institute(
                    "16",
                    "IFCH - Instituto de Filosofia e Ciências Humanas",
                    -22.814970,
                    -47.067984,
                    "Cora Coralina",
                    "100",
                    "13083-896",
                    "582286ca-57ab-49a3-bf27-c19673a8fe49"
                ),
                Institute(
                    "17",
                    "IFGW - Instituto de Física Gleb Wataghin",
                    -22.817561,
                    -47.067180,
                    "Sergio Buarque de Holanda",
                    "777",
                    "13083-859",
                    "8e8876a1-7222-45bd-b7c3-332821d6a752"
                ),
                Institute(
                    "18",
                    "IG - Instituto de Geociências",
                    -22.813279,
                    -47.069016,
                    "Carlos Gomes",
                    "250",
                    "13083-855",
                    "a3ccfe43-618a-43c2-867f-0a17a4b86101"
                ),
                Institute(
                    "19",
                    "IMECC - Instituto de Matemática, Est. e Comp.",
                    -22.815771,
                    -47.067642,
                    "Sergio Buarque de Holanda",
                    "651",
                    "13083-859",
                    "0df2b5aa-4f9c-44bb-b682-0acc12d7501d"
                ),
                Institute(
                    "20",
                    "IQ - Instituto de Química",
                    -22.819143,
                    -47.067883,
                    "Josue de Castro",
                    "126",
                    "13083-861",
                    "9af49468-bbfb-4c25-becf-a5db37de9572"
                )
            )
            institutes.sortedBy { it.getInstituteId().toInt() }
            return institutes
        }

        fun getInstituteNamesList() : MutableList<String> {
            var listInstitutesNames : MutableList<String> = mutableListOf()
            this.getInstitutesList().forEach { listInstitutesNames.add(it.getInstituteName()) }
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