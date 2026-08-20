interface BadgeProps {
  children: string;
  variant?: 'success' | 'warning' | 'danger' | 'neutral';
}

const variants = {
  success: 'bg-success-100 text-success-600',
  warning: 'bg-warning-100 text-warning-600',
  danger: 'bg-danger-100 text-danger-600',
  neutral: 'bg-neutral-100 text-neutral-600',
};

export default function Badge({ children, variant = 'neutral' }: BadgeProps) {
  return (
    <span className={`text-xs font-medium px-2 py-1 rounded ${variants[variant]}`}>
      {children}
    </span>
  );
}