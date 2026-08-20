export default function Spinner({ size = 8 }: { size?: number }) {
  return (
    <div
      className="animate-spin rounded-full border-b-2 border-primary-600"
      style={{ width: size * 4, height: size * 4 }}
    />
  );
}