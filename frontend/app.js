const BFF_URL = "http://localhost:8080/api/bff";
const TOKEN   = "demo-token";

const salida = document.getElementById("salida");

function print(data) {
  salida.textContent = typeof data === "string" ? data : JSON.stringify(data, null, 2);
  salida.classList.add("visible");
}

async function callBff(path, method = "GET", body) {
  try {
    const res = await fetch(`${BFF_URL}${path}`, {
      method,
      headers: { "Content-Type": "application/json", "X-Auth-Token": TOKEN },
      body: body ? JSON.stringify(body) : undefined
    });
    const text = await res.text();
    try { return { status: res.status, data: JSON.parse(text) }; }
    catch { return { status: res.status, data: text }; }
  } catch (e) {
    return { status: 0, error: "No se pudo conectar con el BFF: " + e.message };
  }
}

document.getElementById("btnCrearCita").addEventListener("click", async () => {
  const payload = {
    nombreCliente: document.getElementById("cliente").value,
    nombreMascota: document.getElementById("mascota").value,
    fechaHora: window._fechaHora || new Date().toISOString().slice(0,19),
    motivo: document.getElementById("motivo").value
  };
  print("⏳ Creando cita...");
  const created = await callBff("/citas", "POST", payload);
  if (created.status >= 200 && created.status < 300) {
    const id = created.data?.id || created.data?.citaId || 1;
    const confirmed = await callBff("/citas/" + id + "/confirmar", "PUT");
    print({ creacion: created, confirmacion: confirmed });
  } else {
    print(created);
  }
});

document.getElementById("btnListarCitas").addEventListener("click", async () => {
  print("⏳ Cargando citas...");
  const result = await callBff("/citas", "GET");
  print(result);
});
