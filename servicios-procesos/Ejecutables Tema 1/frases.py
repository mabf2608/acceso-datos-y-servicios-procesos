import random

# Lista de 10 frases famosas
frases_famosas = [
    "La vida es lo que pasa mientras estás ocupado haciendo otros planes. - John Lennon",
    "El único modo de hacer un gran trabajo es amar lo que haces. - Steve Jobs",
    "No cuentes los días, haz que los días cuenten. - Muhammad Ali",
    "Sé el cambio que deseas ver en el mundo. - Mahatma Gandhi",
    "La mente es como un paracaídas: solo funciona si se abre. - Albert Einstein",
    "El éxito es aprender a ir de fracaso en fracaso sin desesperarse. - Winston Churchill",
    "La verdadera sabiduría está en saber que no sabes nada. - Sócrates",
    "No dejes que el ayer ocupe demasiado del hoy. - Will Rogers",
    "La mejor forma de predecir el futuro es creándolo. - Peter Drucker",
    "Haz lo que puedes, con lo que tienes, donde estés. - Theodore Roosevelt"
]

# Seleccionar e imprimir una frase al azar
frase_aleatoria = random.choice(frases_famosas)
print(frase_aleatoria)
