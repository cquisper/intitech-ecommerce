db = db.getSiblingDB('msvc-blog');
db.createCollection('blogs');
db.createCollection('blog_categories');

db = db.getSiblingDB('msvc-coupon');
db.createCollection('coupons');

db = db.getSiblingDB('msvc-enquire');
db.createCollection('enquiries');

db.enquiries.insertMany(
    [
        {
            "_id": {
                "$oid": "6568992f6dccd453915220d4"
            },
            "name": "Christian Palacios",
            "mobile": "465",
            "comment": "hola mundo",
            "email": "cpalacios@mail.com",
            "status": "Submitted",
            "_class": "com.cquisper.msvc.enquire.models.Enquire"
        },
        {
            "_id": {
                "$oid": "6571472f370ca24f30b3429b"
            },
            "name": "Juan Ramirez",
            "mobile": "987564159",
            "comment": "Deseo mas informacion sobre las promociones",
            "email": "jramirez@gmail.com",
            "status": "Submitted",
            "_class": "com.cquisper.msvc.enquire.models.Enquire"
        },
        {
            "_id": {
                "$oid": "657a46552cacdc6439055692"
            },
            "name": "Jose Luyo",
            "mobile": "95737135135",
            "comment": "Deseo mas informacion sobre ofertas",
            "email": "jluyo@mail.com",
            "status": "In Progress",
            "_class": "com.cquisper.msvc.enquire.models.Enquire"
        }
    ]
);

db = db.getSiblingDB('msvc-products');
db.createCollection('products');
db.createCollection('brands');
db.createCollection('colors');
db.createCollection('prod_categories');

db.products.insertMany(
    [
        {
            "_id": {
                "$oid": "657a39b645dacb1343a5fe0f"
            },
            "sku": "jbl-tune-510bt",
            "name": "JBL Tune 510BT",
            "description": "<p><span style=\"background-color: rgb(247, 249, 250); color: rgba(0, 0, 0, 0.87);\">Los auriculares JBL Tune 510BT te permiten disfrutar del potente sonido JBL Pure Bass sin nada que te ate. Fáciles de usar, estos auriculares te ofrecen hasta 40 horas de puro placer y 2 horas más de música con tan solo 5 minutos de carga con el cable USB-C. Si mientras ves un vídeo en otro dispositivo entra una llamada, los auriculares JBL Tune 510BT cambian a tu móvil sin ningún problema. Con Bluetooth 5.0 habilitado y diseñados para ser cómodos, los auriculares JBL Tune 510BT también te permiten conectarte a Siri o Google sin usar tu dispositivo móvil. Disponibles en distintos colores y plegables para un transporte sencillo, los auriculares JBL 510BT son una solución fácil para llevar la música a todos los aspectos de tu vida diaria.</span></p>",
            "category": "Audifono",
            "price": 29,
            "colors": [
                "#000000"
            ],
            "brand": "JBL",
            "tags": "Popular",
            "images": [
                {
                    "publicId": "hyvntnv9ivpyqqportgw",
                    "url": "http://res.cloudinary.com/diphlvnaz/image/upload/v1702508980/hyvntnv9ivpyqqportgw.jpg"
                },
                {
                    "publicId": "shkvmgbd2rxf3owcwqtu",
                    "url": "http://res.cloudinary.com/diphlvnaz/image/upload/v1702508980/shkvmgbd2rxf3owcwqtu.webp"
                }
            ],
            "totalRating": "0",
            "createdAt": {
                "$date": "2023-12-13T23:09:42.042Z"
            },
            "_class": "com.cquisper.msvc.products.models.entities.Product"
        },
        {
            "_id": {
                "$oid": "657a3a7145dacb1343a5fe10"
            },
            "sku": "fitbit-charge-6---rastreador-de-actividad-física-con-aplicaciones-de-google,-frecuencia-cardíaca-en-equipos-de-ejercicio",
            "name": "Fitbit Charge 6 - Rastreador de actividad física con aplicaciones de Google, frecuencia cardíaca en equipos de ejercicio",
            "description": "<ul><li>Muévete más: Frecuencia cardíaca en el equipo a través de Bluetooth, más de 40 modos de ejercicio, GPS integrado, minutos de zona activa, puntaje de preparación diaria, frecuencia cardíaca 24/7, seguimiento de actividad durante todo el día, nivel de ejercicios cardiovasculares, seguimiento automático de ejercicios, mapa de intensidad de entrenamiento y recordatorios para moverse</li><li>Conectado cómodamente: controles de música de Youtube, Google Maps, Google Wallet, notificaciones de llamadas y aplicaciones de teléfono inteligente, pantalla táctil de colores vibrantes con caras de reloj personalizables, temporizador y cronómetro</li><li>Salud: nuestra frecuencia cardíaca más precisa hasta ahora, notificaciones de ECG y ritmo cardíaco irregular, monitoreo de saturación de oxígeno (SpO2), frecuencia cardíaca en reposo y notificaciones de frecuencia cardíaca alta/baja</li><li>7 días de batería y resistente al agua hasta 164.0 ft</li><li>Puntuación de gestión del estrés, puntaje de sueño nocturno, sesión de atención plena en la muñeca, alarma de despertador inteligente, modos de sueño y no molestar</li><li>Incluye membresía Premium de 6 meses para obtener información y orientación más profundas, entrenamientos exclusivos, sesiones de atención plena y más</li><li>Incluido en la caja: Fitbit Charge 6 Tracker (tamaño pequeño y grande correas incluidas). Las correas de muñeca miden pequeñas de 5.512 in a 7.087 in con grandes adicionales de 7.087 in a 8.661 in. Cargador también incluido (compatible con Fitbit Charge 5, no compatible con otros modelos)</li><li>Compatible con iOS 15 o superior y Android OS 9.0 o superior</li></ul>",
            "category": "Reloj",
            "price": 120,
            "colors": [
                "#7d4f4f"
            ],
            "brand": "Huawei",
            "tags": "Especial",
            "images": [
                {
                    "publicId": "fxrilphkqatnzvd0rboc",
                    "url": "http://res.cloudinary.com/diphlvnaz/image/upload/v1702509166/fxrilphkqatnzvd0rboc.jpg"
                }
            ],
            "totalRating": "0",
            "createdAt": {
                "$date": "2023-12-13T23:12:49.581Z"
            },
            "_class": "com.cquisper.msvc.products.models.entities.Product"
        },
        {
            "_id": {
                "$oid": "657a3ae145dacb1343a5fe12"
            },
            "sku": "razer-ornata-v3-x-teclado-para-juegos",
            "name": "Razer Ornata V3 X Teclado para juegos",
            "description": "<ul><li>Teclas de perfil bajo: con teclas más delgadas e interruptores más cortos, disfrute de un posicionamiento natural de la mano que permite largas horas de uso con poca tensión</li><li>Interruptores de membrana silenciosos: perfectos para aquellos que prefieren una experiencia más silenciosa y cómoda al jugar o escribir</li><li>Reposamuñecas ergonómico: se alinea con el teclado y proporciona el lugar perfecto para descansar las muñecas para un apoyo que es vital durante largos períodos de uso</li><li>Diseño duradero y resistente a derrames: la construcción robusta es lo suficientemente resistente para esas sesiones de juego intensas y puede evitar los efectos de derrames menores</li><li>Opciones de enrutamiento de cables: ranuras para meter perfectamente el cable y alimentarlo en cualquier dirección, para que pueda mantener su escritorio libre de desorden y desorden</li><li>Desarrollado por Razer Chroma RGB: con 16.8 millones de colores y un conjunto de efectos para elegir, personaliza el teclado y obtén acceso a efectos de iluminación dinámicos para más de 150 juegos integrados por Chroma</li></ul>",
            "category": "Teclado",
            "price": 34,
            "colors": [
                "#000000"
            ],
            "brand": "Razer",
            "tags": "Destacado",
            "images": [
                {
                    "publicId": "tnroe9gpppsugenskwum",
                    "url": "http://res.cloudinary.com/diphlvnaz/image/upload/v1702509279/tnroe9gpppsugenskwum.jpg"
                }
            ],
            "totalRating": "0",
            "createdAt": {
                "$date": "2023-12-13T23:14:41.142Z"
            },
            "_class": "com.cquisper.msvc.products.models.entities.Product"
        }
    ]
);

db.prod_categories.insertMany(
    [
        {
            "_id": {
                "$oid": "6524af74a30c5844a62c9c87"
            },
            "name": "Laptop",
            "_class": "com.cquisper.msvc.products.models.entities.ProdCategory"
        },
        {
            "_id": {
                "$oid": "6524af7aa30c5844a62c9c88"
            },
            "name": "Monitor",
            "_class": "com.cquisper.msvc.products.models.entities.ProdCategory"
        },
        {
            "_id": {
                "$oid": "6524af7ea30c5844a62c9c89"
            },
            "name": "Mouse",
            "_class": "com.cquisper.msvc.products.models.entities.ProdCategory"
        },
        {
            "_id": {
                "$oid": "65260e3b1d312872caafbf4a"
            },
            "name": "Reloj",
            "_class": "com.cquisper.msvc.products.models.entities.ProdCategory"
        },
        {
            "_id": {
                "$oid": "652b5f419c09713d09b22e68"
            },
            "name": "Teclado",
            "_class": "com.cquisper.msvc.products.models.entities.ProdCategory"
        },
        {
            "_id": {
                "$oid": "657a380045dacb1343a5fe0c"
            },
            "name": "Audifono",
            "_class": "com.cquisper.msvc.products.models.entities.ProdCategory"
        }
    ]
);

db.brands.insertMany(
    [
        {
            "_id": {
                "$oid": "6524afa2a30c5844a62c9c8a"
            },
            "name": "Samsung",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "6524afa6a30c5844a62c9c8b"
            },
            "name": "Apple",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "6524fad97250582dd7a35f7b"
            },
            "name": "Huawei",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "6524faf67250582dd7a35f7c"
            },
            "name": "Sony",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "6524ff237250582dd7a35f7f"
            },
            "name": "ACER",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "652b3fac9c09713d09b22e5c"
            },
            "name": "Intel",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "657a37ef45dacb1343a5fe0b"
            },
            "name": "JBL",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        },
        {
            "_id": {
                "$oid": "657a3a8c45dacb1343a5fe11"
            },
            "name": "Razer",
            "_class": "com.cquisper.msvc.products.models.entities.Brand"
        }
    ]
);

db.brands.insertMany(
    [
        {
            "_id": {
                "$oid": "652613c31d312872caafbf4c"
            },
            "name": "#cc2828",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        },
        {
            "_id": {
                "$oid": "652613ec1d312872caafbf4d"
            },
            "name": "#a94242",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        },
        {
            "_id": {
                "$oid": "652614281d312872caafbf4e"
            },
            "name": "#801919",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        },
        {
            "_id": {
                "$oid": "6527f997b6369352ba60f30b"
            },
            "name": "#7d4f4f",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        },
        {
            "_id": {
                "$oid": "6527f9afb6369352ba60f30c"
            },
            "name": "#ffae00",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        },
        {
            "_id": {
                "$oid": "652b69c4c415aa131a4911ed"
            },
            "name": "#00fbff",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        },
        {
            "_id": {
                "$oid": "657a380f45dacb1343a5fe0d"
            },
            "name": "#000000",
            "_class": "com.cquisper.msvc.products.models.entities.Color"
        }
    ]
);

db = db.getSiblingDB('msvc-ratings');
db.createCollection('ratings');

db.ratings.insertMany(
    [
        {
            "_id": {
                "$oid": "65127d7f7c99c1489efa581e"
            },
            "star": 5,
            "comment": "¡Estoy completamente enamorado de mi nuevo MacBook Air! La potencia del chip M1 y la calidad de la pantalla Retina son impresionantes. Además, el diseño en color Space Gray es simplemente elegante. ¡Una compra excelente!",
            "idUser": {
                "$numberLong": "1"
            },
            "idProduct": "651266d9b096863bebc8012a",
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "65134789d7320e6c79c9ea82"
            },
            "star": 4,
            "comment": "¡Estoy completamente enamorado de mi nuevo MacBook Air! La potencia del chip M1 y la calidad de la pantalla Retina son impresionantes. Además, el diseño en color Space Gray es simplemente elegante. ¡Una compra excelente!",
            "idUser": {
                "$numberLong": "1"
            },
            "idProduct": "65126c86424cc94420ae959b",
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "651f871f8a10a14ba16cdcf7"
            },
            "star": 5,
            "comment": "10 de 10",
            "idProduct": "651266d9b096863bebc8012a",
            "user": {
                "_id": {
                    "$numberLong": "1"
                },
                "firstName": "Fabricio",
                "lastName": "Sifuentes",
                "email": "fsifuentes@mail.com"
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "651f8d588a10a14ba16cdcf8"
            },
            "star": 5,
            "comment": "10 de 10",
            "idProduct": "65126729b096863bebc8012b",
            "user": {
                "_id": {
                    "$numberLong": "1"
                },
                "firstName": "Fabricio",
                "lastName": "Sifuentes",
                "email": "fsifuentes@mail.com"
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "6528157b284b06644b21111a"
            },
            "star": 5,
            "comment": "10 de 10",
            "idProduct": "6524dd18add3cc5d551b855e",
            "user": {
                "_id": {
                    "$numberLong": "1"
                },
                "firstName": "Fabricio",
                "lastName": "Sifuentes",
                "email": "fsifuentes@mail.com"
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "65281ee415b4d326bf8b543c"
            },
            "star": 5,
            "comment": "10 de 10",
            "idProduct": "6524e2af7250582dd7a35f78",
            "user": {
                "_id": {
                    "$numberLong": "1"
                },
                "firstName": "Fabricio",
                "lastName": "Sifuentes",
                "email": "fsifuentes@mail.com",
                "wishList": [
                    "651266d9b096863bebc8012a",
                    "6512675ab096863bebc8012d"
                ]
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "65755e86dbfcb3412d87e1c3"
            },
            "star": 5,
            "comment": "yes",
            "idProduct": "6567c08e72b53153e4c181a0",
            "user": {
                "_id": {
                    "$numberLong": "18"
                },
                "firstName": "Emilio",
                "lastName": "Hernandez",
                "email": "em@mail.com",
                "wishList": [
                    "6524dd18add3cc5d551b855e",
                    "655587985cf7f3414f40b0b0",
                    "652daac35e05ed5a016ede84"
                ]
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "657662cb420c396ee3ee15a3"
            },
            "star": 5,
            "comment": "Excelente producto",
            "idProduct": "6573de8581e9d044ba70ff3e",
            "user": {
                "_id": {
                    "$numberLong": "18"
                },
                "firstName": "Emilio",
                "lastName": "Hernandez",
                "email": "em@mail.com",
                "wishList": [
                    "6524dd18add3cc5d551b855e",
                    "655587985cf7f3414f40b0b0",
                    "652daac35e05ed5a016ede84"
                ]
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "65766ba7420c396ee3ee15a4"
            },
            "star": 5,
            "comment": "10 de 10",
            "idProduct": "6567af1472b53153e4c18197",
            "user": {
                "_id": {
                    "$numberLong": "1"
                },
                "firstName": "Fabricio",
                "lastName": "Sifuentes",
                "email": "fsifuentes@mail.com",
                "wishList": [
                    "651266d9b096863bebc8012a",
                    "6512675ab096863bebc8012d"
                ]
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "65766d60420c396ee3ee15a5"
            },
            "star": 5,
            "comment": "10 de 10",
            "idProduct": "6567ae2e72b53153e4c1818e",
            "user": {
                "_id": {
                    "$numberLong": "1"
                },
                "firstName": "Fabricio",
                "lastName": "Sifuentes",
                "email": "fsifuentes@mail.com",
                "wishList": [
                    "651266d9b096863bebc8012a",
                    "6512675ab096863bebc8012d"
                ]
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        },
        {
            "_id": {
                "$oid": "657a4552a8027b5e9ef2062c"
            },
            "star": 4,
            "comment": "muy buen prodcto",
            "idProduct": "657a3ae145dacb1343a5fe12",
            "user": {
                "_id": {
                    "$numberLong": "18"
                },
                "firstName": "Emilio",
                "lastName": "Hernandez",
                "email": "em@mail.com",
                "wishList": [
                    "6524dd18add3cc5d551b855e",
                    "655587985cf7f3414f40b0b0",
                    "652daac35e05ed5a016ede84"
                ]
            },
            "_class": "com.cquisper.msvc.ratings.models.entities.Rating"
        }
    ]
);