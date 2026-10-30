export type OrderSide = "BUY" | "SELL";
export type OrderType = "MARKET" | "LIMIT";

export type Order = {
  id: string;
  clientOrderId: string;
  symbol: string;
  side: OrderSide;
  type: OrderType;
  quantity: number;
  limitPrice?: number;
  status: string;
  createdAt: string;
  updatedAt: string;
};

export type CreateOrderRequest = Omit<Order, "id" | "status" | "createdAt" | "updatedAt">;

export async function createOrder(request: CreateOrderRequest): Promise<Order> {
  const response = await fetch("/api/v1/orders", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(request)
  });
  if (!response.ok) throw new Error(await response.text() || "Unable to create order");
  return response.json();
}

export async function getOrder(id: string): Promise<Order> {
  const response = await fetch(`/api/v1/orders/${id}`);
  if (!response.ok) throw new Error("Unable to load order status");
  return response.json();
}
