const BFF_URL = "http://localhost:8080/api/bff";
const TOKEN   = "demo-token";

const salida = document.getElementById("salida");

function print(data) {
  salida.textContent = typeof data === "string" ? data : JSON.stringify(data, null, 2);
  salida.classList.add("visible");
}

function showSuccess(cita) {
  salida.innerHTML = `
    <div style="background:#f0fdf4;border:1px solid #86efac;border-radius:10px;padding:20px;color:#166534;font-family:inherit;">
      <div style="font-size:2rem;margin-bottom:8px;">✅</div>
      <div style="font-size:1.1rem;font-weight:600;margin-bottom:4px;">¡Cita confirmada!</div>
      <div style="font-size:0.9rem;color:#15803d;">
        <strong>${cita.nombreCliente}</strong> y <strong>${cita.nombreMascota}</strong><br>
        📅 ${new Date(cita.fechaHora).toLocaleString('es-CL', {weekday:'long',day:'numeric',month:'long',hour:'2-digit',minute:'2-digit'})}<br>
        📋 ${cita.motivo}<br>
        🆔 Cita #${cita.id} — Estado: <strong>${cita.estado}</strong>
      </div>
    </div>`;
  salida.classList.add("visible");
}

function showList(citas) {
  if (!Array.isArray(citas) || citas.length === 0) {
    salida.innerHTML = `<div style="padding:16px;color:#6b7280;font-style:italic;">No hay citas registradas aún.</div>`;
    salida.classList.add("visible");
    return;
  }
  const rows = citas.map(c => `
    <div style="border:1px solid #e5e7eb;border-radius:8px;padding:12px;margin-bottom:8px;">
      <strong>#${c.id} — ${c.nombreCliente}</strong> / ${c.nombreMascota}<br>
      <span style="color:#6b7280;font-size:0.85rem;">
        ${new Date(c.fechaHora).toLocaleString('es-CL',{weekday:'long',day:'numeric',month:'long',hour:'2-digit',minute:'2-digit'})}
        · ${c.motivo} · <em>${c.estado}</em>
      </span>
    </div>`).join('');
  salida.innerHTML = `<div style="font-weight:600;margin-bottom:10px;">📋 Citas registradas (${citas.length})</div>${rows}`;
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
    if (confirmed.status >= 200 && confirmed.status < 300) {
      showSuccess(confirmed.data);
    } else {
      print(confirmed);
    }
  } else {
    print(created);
  }
});

document.getElementById("btnListarCitas").addEventListener("click", async () => {
  print("⏳ Cargando citas...");
  const result = await callBff("/citas", "GET");
  if (result.status >= 200 && result.status < 300) {
    showList(result.data);
  } else {
    print(result);
  }
});
