const BFF_URL = "http://localhost:8080/api/bff";
const TOKEN = "demo-token";

const salida = document.getElementById("salida");

function print(data) {
  salida.textContent = typeof data === "string" ? data : JSON.stringify(data, null, 2);
}

async function callBff(path, method = "GET", body) {
  const res = await fetch(`${BFF_URL}${path}`, {
    method,
    headers: {
      "Content-Type": "application/json",
      "X-Auth-Token": TOKEN
    },
    body: body ? JSON.stringify(body) : undefined
  });
  const text = await res.text();

  try {
    return { status: res.status, data: JSON.parse(text) };
  } catch {
    return { status: res.status, data: text };
  }
}

document.getElementById("btnCrearCita").addEventListener("click", async () => {
  const payload = {
    nombreCliente: document.getElementById("cliente").value,
    nombreMascota: document.getElementById("mascota").value,
    fechaHora: document.getElementById("fechaHora").value,
    motivo: document.getElementById("motivo").value
  };

  const result = await callBff("/citas", "POST", payload);
  print(result);
});

document.getElementById("btnConfirmarCita").addEventListener("click", async () => {
  const result = await callBff("/citas/1/confirmar", "PUT");
  print(result);
});

document.getElementById("btnListarCitas").addEventListener("click", async () => {
  const result = await callBff("/citas", "GET");
  print(result);
});
