import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Biblioteca extends JFrame implements ActionListener {

    private JTextField txtTituloLibro;
    private JTextField txtAutorLibro;
    private JTextField txtIdUsuario;
    private JTextField txtTituloPrestamo;
    private JTextField txtIdUsuarioPrestamo;
    private JTextField txtTituloDevolucion;
    private JTextArea txtAreaResultado;
    private List<Libro> libros;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;

    public Biblioteca() {
        super("Biblioteca");

        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Crear paneles tabulados
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab de Registrar Libro
        tabbedPane.addTab("Registrar Libro", crearPanelRegistroLibro());

        // Tab de Registrar Usuario
        tabbedPane.addTab("Registrar Usuario", crearPanelRegistroUsuario());

        // Tab de Realizar Prestamo
        tabbedPane.addTab("Realizar Prestamo", crearPanelPrestamo());

        // Tab de Devolver Libro
        tabbedPane.addTab("Devolver Libro", crearPanelDevolucion());

        // Tab de Inventario
        tabbedPane.addTab("Inventario", crearPanelInventario());

        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResultado);

        add(tabbedPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel crearPanelRegistroLibro() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearFormularioRegistroLibro(), BorderLayout.CENTER);
        panel.add(crearBoton("Registrar Libro"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelRegistroUsuario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearFormularioRegistroUsuario(), BorderLayout.CENTER);
        panel.add(crearBoton("Registrar Usuario"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelPrestamo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearFormularioPrestamo(), BorderLayout.CENTER);
        panel.add(crearBoton("Realizar Prestamo"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelDevolucion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearFormularioDevolucion(), BorderLayout.CENTER);
        panel.add(crearBoton("Devolver Libro"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearBoton("Inventario"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearFormularioRegistroLibro() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Titulo:"));
        txtTituloLibro = new JTextField();
        panel.add(txtTituloLibro);
        panel.add(new JLabel("Autor:"));
        txtAutorLibro = new JTextField();
        panel.add(txtAutorLibro);
        return panel;
    }

    private JPanel crearFormularioRegistroUsuario() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(new JLabel("Usuario ID:"));
        txtIdUsuario = new JTextField();
        panel.add(txtIdUsuario);
        return panel;
    }

    private JPanel crearFormularioPrestamo() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        return panel;
    }

    private JPanel crearFormularioDevolucion() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        return panel;
    }

    private JButton crearBoton(String accion) {
        JButton boton = new JButton(accion);
        boton.addActionListener(this);
        return boton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Registrar Libro":
                registrarLibro();
                break;
            case "Registrar Usuario":
                registrarUsuario();
                break;
            case "Realizar Prestamo":
                realizarPrestamo();
                break;
            case "Devolver Libro":
                devolverLibro();
                break;
            case "Inventario":
                mostrarInventario();
                break;
        }
    }

    private void registrarLibro() {
        String titulo = txtTituloLibro.getText();
        String autor = txtAutorLibro.getText();
        if (!titulo.isEmpty() && !autor.isEmpty()) {
            libros.add(new Libro(titulo, autor));
            txtTituloLibro.setText("");
            txtAutorLibro.setText("");
            txtAreaResultado.setText("Libro registrado con éxito.");
        } else {
            txtAreaResultado.setText("Por favor, complete todos los campos.");
        }
    }

    private void registrarUsuario() {
        String id = txtIdUsuario.getText();
        if (!id.isEmpty()) {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
            String apellido = JOptionPane.showInputDialog("Ingrese el apellido del usuario:");
            if (nombre != null && apellido != null && !nombre.isEmpty() && !apellido.isEmpty()) {
                usuarios.add(new Usuario(nombre, apellido, id));
                txtIdUsuario.setText("");
                txtAreaResultado.setText("Usuario registrado con éxito.");
            } else {
                txtAreaResultado.setText("Por favor, complete todos los campos.");
            }
        } else {
            txtAreaResultado.setText("Por favor, ingrese el ID del usuario.");
        }
    }

    private void realizarPrestamo() {
        if (libros.isEmpty() || usuarios.isEmpty()) {
            txtAreaResultado.setText("No hay libros o usuarios disponibles para realizar préstamos.");
            return;
        }

        Libro libro = (Libro) JOptionPane.showInputDialog(this, "Seleccione un libro:", "Realizar Préstamo",
                JOptionPane.PLAIN_MESSAGE, null, libros.toArray(), null);

        if (libro != null) {
            Usuario usuario = (Usuario) JOptionPane.showInputDialog(this, "Seleccione un usuario:", "Realizar Préstamo",
                    JOptionPane.PLAIN_MESSAGE, null, usuarios.toArray(), null);

            if (usuario != null) {
                prestamos.add(new Prestamo(libro, usuario, LocalDate.now(), LocalDate.now().plusWeeks(2)));
                txtAreaResultado.setText("Préstamo realizado con éxito.");
            } else {
                txtAreaResultado.setText("Préstamo cancelado.");
            }
        } else {
            txtAreaResultado.setText("Préstamo cancelado.");
        }
    }

    private void devolverLibro() {
        if (prestamos.isEmpty()) {
            txtAreaResultado.setText("No hay préstamos activos.");
            return;
        }

        Prestamo prestamo = (Prestamo) JOptionPane.showInputDialog(this, "Seleccione un préstamo a devolver:", "Devolver Libro",
                JOptionPane.PLAIN_MESSAGE, null, prestamos.toArray(), null);

        if (prestamo != null) {
            prestamos.remove(prestamo);
            txtAreaResultado.setText("Libro devuelto con éxito.");
        } else {
            txtAreaResultado.setText("Devolución cancelada.");
        }
    }

    private void mostrarInventario() {
        StringBuilder inventario = new StringBuilder("Inventario de libros:\n");
        for (Libro libro : libros) {
            inventario.append(libro).append("\n");
        }
        txtAreaResultado.setText(inventario.toString());
    }

    private Libro buscarLibro(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    private Usuario buscarUsuario(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equalsIgnoreCase(id)) {
                return usuario;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Biblioteca();
        });
    }
}

class Libro {
    private String titulo;
    private String autor;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return titulo + " - " + autor;
    }
}

class Usuario {
    private String nombre;
    private String apellido;
    private String id;

    public Usuario(String nombre, String apellido, String id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellido;
    }
}

class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    @Override
    public String toString() {
        return libro.getTitulo() + " prestado a " + usuario.getId() + " hasta " + fechaDevolucion;
    }
}
