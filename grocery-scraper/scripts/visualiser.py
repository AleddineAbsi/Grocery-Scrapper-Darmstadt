import os
import pandas as pd
import matplotlib.pyplot as plt

#find path to data
base_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
csv_path = os.path.join(base_dir, "data", "produits.csv")

if not os.path.exists(csv_path):
    raise FileNotFoundError(f"Le fichier n'existe pas : {csv_path}")

#lecture
df = pd.read_csv(csv_path, sep=";")
print(df.columns)
print(df["price"].head())
print(df["price"].dtype)

plt.xticks(rotation=45)
plt.hist(df["price"], bins=10, color="skyblue")
plt.title("Distribution of price")
plt.xlabel("Price €")
plt.ylabel("Number of Product")
plt.grid(True)
plt.show()