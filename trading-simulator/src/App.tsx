import { FormEvent, useEffect, useState } from "react";
import { createOrder, getOrder, Order, OrderSide, OrderType } from "./api";

const initialForm = {
  clientOrderId: `sim-${Date.now()}`,
  symbol: "AAPL",
  side: "BUY" as OrderSide,
  type: "LIMIT" as OrderType,
  quantity: "10",
  limitPrice: "180.50"
};

export default function App() {
  const [form, setForm] = useState(initialForm);
  const [orders, setOrders] = useState<Order[]>([]);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const timer = window.setInterval(async () => {
      if (!orders.length) return;
      try {
        const updated = await Promise.all(orders.map((order) => getOrder(order.id)));
        setOrders(updated);
      } catch {
        // The table keeps the last known state if the service is temporarily unavailable.
      }
    }, 3000);
    return () => window.clearInterval(timer);
  }, [orders]);

  function update(field: string, value: string) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function submit(event: FormEvent) {
    event.preventDefault();
    setBusy(true);
    setError("");
    try {
      const order = await createOrder({
        clientOrderId: form.clientOrderId,
        symbol: form.symbol,
        side: form.side,
        type: form.type,
        quantity: Number(form.quantity),
        ...(form.type === "LIMIT" ? { limitPrice: Number(form.limitPrice) } : {})
      });
      setOrders((current) => [order, ...current.filter((item) => item.id !== order.id)]);
      setForm({ ...initialForm, clientOrderId: `sim-${Date.now()}` });
    } catch (e) {
      setError(e instanceof Error ? e.message : "Unable to create order");
    } finally {
      setBusy(false);
    }
  }

  return (
    <main className="shell">
      <header className="hero">
        <div>
          <p className="eyebrow">ORDER MANAGEMENT PLATFORM</p>
          <h1>Trading Simulator</h1>
          <p className="subtitle">Create and track orders through the event-driven order service.</p>
        </div>
        <span className="connection"><i /> API :8080</span>
      </header>

      <section className="grid">
        <form className="card form-card" onSubmit={submit}>
          <div className="card-heading"><h2>Create order</h2><span>REST → Kafka</span></div>
          <label>Client order ID<input value={form.clientOrderId} onChange={(e) => update("clientOrderId", e.target.value)} required /></label>
          <div className="two-col">
            <label>Symbol<input value={form.symbol} onChange={(e) => update("symbol", e.target.value.toUpperCase())} maxLength={20} required /></label>
            <label>Side<select value={form.side} onChange={(e) => update("side", e.target.value)}><option>BUY</option><option>SELL</option></select></label>
          </div>
          <div className="two-col">
            <label>Order type<select value={form.type} onChange={(e) => update("type", e.target.value)}><option>LIMIT</option><option>MARKET</option></select></label>
            <label>Quantity<input type="number" min="0.000001" step="any" value={form.quantity} onChange={(e) => update("quantity", e.target.value)} required /></label>
          </div>
          {form.type === "LIMIT" && <label>Limit price<input type="number" min="0.000001" step="any" value={form.limitPrice} onChange={(e) => update("limitPrice", e.target.value)} required /></label>}
          {error && <p className="error">{error}</p>}
          <button disabled={busy}>{busy ? "Submitting…" : "Submit order"}</button>
        </form>

        <section className="card orders-card">
          <div className="card-heading"><h2>Order activity</h2><span>{orders.length} tracked</span></div>
          {!orders.length ? <div className="empty"><strong>No orders yet</strong><span>Submit an order to see its lifecycle here.</span></div> : <div className="order-list">{orders.map((order) => <article className="order-row" key={order.id}><div><strong>{order.side} {order.quantity} {order.symbol}</strong><small>{order.type} · {order.clientOrderId}</small></div><span className={`status status-${order.status.toLowerCase()}`}>{order.status}</span></article>)}</div>}
        </section>
      </section>
    </main>
  );
}
