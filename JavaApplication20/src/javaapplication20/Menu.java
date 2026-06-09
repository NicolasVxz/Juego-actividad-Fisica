/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaapplication20;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author NICOLAS
 */
public class Menu extends javax.swing.JFrame {
    
    private Usuario usuario;
    private Estudiante estudiante;
    private Docente docente;
    private PadreFamilia padre;
    private Actividad actividadActual;
    private Mision misionActual;
            
        
private Actividad[] actividades = {
    new Actividad("Jumping jack", "Haz 10 Jumping jack", 20, "videos/jumping jack.mp4"),
    new Actividad("Sentadillas", "Haz 15 sentadillas", 30, "videos/sentadillas.mp4"),
    new Actividad("Abdominales", "Haz 20 abdominales", 30, "videos/abdominales.mp4"),
    new Actividad("Burpees", "Haz 20 Burpees", 25, "videos/burpees.mp4")
    };
private void cargarTablaUsuarios(){
    try{
        BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));
         DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
         modelo.setRowCount(0);
         String linea;
         while((linea = br.readLine()) != null){
             String[] datos = linea.split(",");
             String usuario = datos[0];
            String clave = datos[1];
            String rol = datos[2];
            if(rol.equalsIgnoreCase("ESTUDIANTE")){
                String padre = "";
                if(datos.length == 4){
                    padre = datos[3];
                }
                modelo.addRow(new Object[]{
                    usuario,
                    clave,
                    rol,
                    padre
                });
            }
         }
         br.close();
    }catch(Exception e){
        System.out.println("Error al cargar tabla");
    }
}
private void cargarRelaciones(){
    try{
        BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));
        String linea;
        String texto = "";
        while((linea = br.readLine()) != null){
            String[] datos = linea.split(",");
            if(datos.length == 4){
                String usuario = datos[0];
                String rol = datos[2];
                String relacion = datos[3];
                if(rol.equalsIgnoreCase("ESTUDIANTE")){
                    texto += "Alumno: " + usuario + " → Padre: " + relacion + "\n";
                }
            }
        }
        txtRelacion.setText(texto);
        br.close();
    }catch(Exception e){
         System.out.println("Error al cargar relaciones");
    }
}
private void cargarProgreso(){
     try{
         BufferedReader br = new BufferedReader(new FileReader("progreso.txt"));
         String linea;
         while((linea = br.readLine()) != null){
             String[] datos = linea.split(",");
             if(datos[0].equals(estudiante.getNombre())){
                 int puntaje = Integer.parseInt(datos[1]);
                 estudiante.setPuntaje(puntaje);
                 estudiante.sumarPuntos(0);
             }
         }
         br.close();
     }catch(Exception e){
         System.out.println("Sin progreso guardado");
         nuevaMision();
         lblNivelJu.setText(String.valueOf(estudiante.getNivel()));
         lblPuntajeJu.setText(String.valueOf(estudiante.getPuntaje()));
     } 
}
private void guardarProgreso(){
    try{
        BufferedReader br = new BufferedReader(new FileReader("progreso.txt"));
        java.util.ArrayList<String> lineas = new java.util.ArrayList<>();
        String linea;
        boolean encontrado = false;
        while((linea = br.readLine()) != null){
            String[] datos = linea.split(",");
            if(datos[0].equals(estudiante.getNombre())){
                //Reemplaza si ya existe
                lineas.add(estudiante.getNombre() + "," 
                        + estudiante.getPuntaje() + ","
                        + estudiante.getNivel());
                encontrado = true;
            }else {
                lineas.add(linea);
            }
        }
        br.close();
        //Si no existe lo agrega
         if(!encontrado){
             lineas.add(estudiante.getNombre() + "," 
                     + estudiante.getPuntaje() + "," 
                     + estudiante.getNivel());
         }
         //Reescribe todo
         java.io.PrintWriter pw = new java.io.PrintWriter("progreso.txt");
         for(String l : lineas){
             pw.println(l);
         }
         pw.close();
    }catch(Exception e){
        System.out.println("Error al guardar progreso");
    }
}
private void nuevaMision(){
    int random = (int)(Math.random() * actividades.length);
    actividadActual = actividades[random];
    misionActual = new Mision(actividadActual);
    lblMision.setText(actividadActual.getDescripcion());
}
    public Menu(Usuario usuario) {
        initComponents();
        PanelAdmin.setVisible(false);
        PanelEstudiante.setVisible(false);
        this.usuario = usuario;
        estudiante = new Estudiante(usuario.getUsuario());
        docente = new Docente(usuario.getUsuario());
        padre = new PadreFamilia(usuario.getUsuario(), estudiante);
        cargarDatos ();
    }
    private void cargarDatos(){
        if(usuario == null){
        JOptionPane.showMessageDialog(this, "Error: usuario no recibido");
        return;
    }
    String nombre = usuario.getUsuario();
    String rol = usuario.getRol();

    if(rol == null){
        JOptionPane.showMessageDialog(this, "Error: rol vacío");
        return;

    }
    if(rol.equalsIgnoreCase("ADMIN")){
        PanelAdmin.setVisible(true);
        PanelEstudiante.setVisible(false);
        PanelPadre.setVisible(false);
        lblNombreAdministrador.setText(nombre);
        cargarTablaUsuarios();
        cargarRelaciones();
    }else if(rol.equalsIgnoreCase("PADRE")){
        PanelAdmin.setVisible(false);
        PanelEstudiante.setVisible(false);
        PanelPadre.setVisible(true);
        lblNombrePadre.setText(nombre);
    }
    else {
        PanelAdmin.setVisible(false);
        PanelEstudiante.setVisible(true);
        PanelPadre.setVisible(false);
        cargarProgreso();

        lblNombreA.setText(" " + estudiante.getNombre());
        lblPuntajeJu.setText(String.valueOf(estudiante.getPuntaje()));
        lblNivelJu.setText(String.valueOf(estudiante.getNivel()));
        nuevaMision();
    }
}

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Menu.class.getName());

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelEstudiante = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombreJugador = new javax.swing.JLabel();
        lblNivel = new javax.swing.JLabel();
        lblPuntaje = new javax.swing.JLabel();
        lbltitulo = new javax.swing.JLabel();
        btnCompletarMision = new javax.swing.JButton();
        btnVerProgreso = new javax.swing.JButton();
        btnCerrarSrsion = new javax.swing.JButton();
        lblNombreA = new javax.swing.JLabel();
        lblNivelJu = new javax.swing.JLabel();
        lblPuntajeJu = new javax.swing.JLabel();
        lblMision = new javax.swing.JLabel();
        btnVerInsignia = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        PanelAdmin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblNombreAdministrador = new javax.swing.JLabel();
        btnRegristarAlumnos = new javax.swing.JButton();
        btnGestionarUsuarios = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnVerReport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRelacion = new javax.swing.JTextArea();
        PanelPadre = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCerrarPadre = new javax.swing.JButton();
        lblNombrePadre = new javax.swing.JLabel();
        btnReporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        PanelEstudiante.setBackground(new java.awt.Color(30, 30, 60));
        PanelEstudiante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Juego Actividad  Fisica");

        lblNombreJugador.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblNombreJugador.setText("Jugador:");

        lblNivel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblNivel.setText("Nivel:");

        lblPuntaje.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblPuntaje.setText("Puntaje:");

        lbltitulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbltitulo.setText("Mision Actual:");

        btnCompletarMision.setBackground(new java.awt.Color(102, 255, 102));
        btnCompletarMision.setForeground(new java.awt.Color(0, 0, 0));
        btnCompletarMision.setText("✅ COMPLETAR MISION");
        btnCompletarMision.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCompletarMision.addActionListener(this::btnCompletarMisionActionPerformed);

        btnVerProgreso.setBackground(new java.awt.Color(51, 102, 255));
        btnVerProgreso.setForeground(new java.awt.Color(0, 0, 0));
        btnVerProgreso.setText("📊 VER PROGRESO");
        btnVerProgreso.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVerProgreso.addActionListener(this::btnVerProgresoActionPerformed);

        btnCerrarSrsion.setBackground(new java.awt.Color(204, 0, 0));
        btnCerrarSrsion.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrarSrsion.setText("🔒 CERRAR SESIÓN");
        btnCerrarSrsion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarSrsion.addActionListener(this::btnCerrarSrsionActionPerformed);

        lblNombreA.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblNombreA.setText("---");

        lblNivelJu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblNivelJu.setForeground(new java.awt.Color(102, 255, 102));
        lblNivelJu.setText("0");

        lblPuntajeJu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblPuntajeJu.setForeground(new java.awt.Color(102, 255, 102));
        lblPuntajeJu.setText("0");

        lblMision.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMision.setText("---");

        btnVerInsignia.setBackground(new java.awt.Color(51, 102, 255));
        btnVerInsignia.setForeground(new java.awt.Color(0, 0, 0));
        btnVerInsignia.setText("🏅 VER INSIGNIAS");
        btnVerInsignia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVerInsignia.addActionListener(this::btnVerInsigniaActionPerformed);

        btnVer.setBackground(new java.awt.Color(51, 102, 255));
        btnVer.setForeground(new java.awt.Color(0, 0, 0));
        btnVer.setText("▶ VER EJERCICIO");
        btnVer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVer.addActionListener(this::btnVerActionPerformed);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication20/Imagenes/aaaaa.jpg"))); // NOI18N
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout PanelEstudianteLayout = new javax.swing.GroupLayout(PanelEstudiante);
        PanelEstudiante.setLayout(PanelEstudianteLayout);
        PanelEstudianteLayout.setHorizontalGroup(
            PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEstudianteLayout.createSequentialGroup()
                .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEstudianteLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149))
                    .addGroup(PanelEstudianteLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEstudianteLayout.createSequentialGroup()
                                .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(lblNombreA, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelEstudianteLayout.createSequentialGroup()
                                .addComponent(lblMision, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelEstudianteLayout.createSequentialGroup()
                                .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(PanelEstudianteLayout.createSequentialGroup()
                                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNivel)
                                            .addComponent(lblPuntaje))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPuntajeJu)
                                            .addComponent(lblNivelJu)))
                                    .addComponent(lbltitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnVerInsignia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCompletarMision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnVerProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCerrarSrsion, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)))
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
        PanelEstudianteLayout.setVerticalGroup(
            PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEstudianteLayout.createSequentialGroup()
                .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEstudianteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreJugador)
                            .addComponent(lblNombreA))
                        .addGap(18, 18, 18)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNivel)
                            .addComponent(lblNivelJu))
                        .addGap(21, 21, 21)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPuntaje)
                            .addComponent(lblPuntajeJu))
                        .addGap(53, 53, 53)
                        .addComponent(lbltitulo)
                        .addGap(22, 22, 22)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMision)
                            .addComponent(btnVer))
                        .addGap(44, 44, 44)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCompletarMision)
                            .addComponent(btnVerProgreso))
                        .addGap(18, 18, 18)
                        .addGroup(PanelEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVerInsignia)
                            .addComponent(btnCerrarSrsion)))
                    .addGroup(PanelEstudianteLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        PanelAdmin.setBackground(new java.awt.Color(30, 30, 60));
        PanelAdmin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Panel Administrador");

        lblNombreAdministrador.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblNombreAdministrador.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreAdministrador.setText("Administrador:");

        btnRegristarAlumnos.setBackground(new java.awt.Color(51, 153, 255));
        btnRegristarAlumnos.setForeground(new java.awt.Color(0, 0, 0));
        btnRegristarAlumnos.setText("REGISTRAR ALUMNOS");
        btnRegristarAlumnos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegristarAlumnos.addActionListener(this::btnRegristarAlumnosActionPerformed);

        btnGestionarUsuarios.setBackground(new java.awt.Color(51, 153, 255));
        btnGestionarUsuarios.setForeground(new java.awt.Color(0, 0, 0));
        btnGestionarUsuarios.setText("GESTIONAR USUARIO");
        btnGestionarUsuarios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGestionarUsuarios.addActionListener(this::btnGestionarUsuariosActionPerformed);

        btnCerrarSesion.setBackground(new java.awt.Color(255, 0, 51));
        btnCerrarSesion.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrarSesion.setText("CERRAR SESION");
        btnCerrarSesion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarSesion.addActionListener(this::btnCerrarSesionActionPerformed);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("---");

        btnVerReport.setBackground(new java.awt.Color(51, 153, 255));
        btnVerReport.setForeground(new java.awt.Color(0, 0, 0));
        btnVerReport.setText("VER REPORTE");
        btnVerReport.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVerReport.addActionListener(this::btnVerReportActionPerformed);

        tablaUsuarios.setBackground(new java.awt.Color(51, 153, 255));
        tablaUsuarios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Alumno", "Contraseña", "Rol"
            }
        ));
        jScrollPane1.setViewportView(tablaUsuarios);

        txtRelacion.setBackground(new java.awt.Color(51, 153, 255));
        txtRelacion.setColumns(20);
        txtRelacion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtRelacion.setRows(5);
        jScrollPane2.setViewportView(txtRelacion);

        javax.swing.GroupLayout PanelAdminLayout = new javax.swing.GroupLayout(PanelAdmin);
        PanelAdmin.setLayout(PanelAdminLayout);
        PanelAdminLayout.setHorizontalGroup(
            PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAdminLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAdminLayout.createSequentialGroup()
                        .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAdminLayout.createSequentialGroup()
                                .addComponent(lblNombreAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAdminLayout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelAdminLayout.createSequentialGroup()
                        .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRegristarAlumnos, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                            .addComponent(btnGestionarUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                        .addGap(37, 37, 37))))
        );
        PanelAdminLayout.setVerticalGroup(
            PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAdminLayout.createSequentialGroup()
                        .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreAdministrador)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(btnRegristarAlumnos)
                        .addGap(18, 18, 18)
                        .addComponent(btnGestionarUsuarios))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAdminLayout.createSequentialGroup()
                        .addComponent(btnVerReport)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrarSesion))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        PanelPadre.setBackground(new java.awt.Color(30, 30, 60));
        PanelPadre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Padre de familia:");

        btnCerrarPadre.setBackground(new java.awt.Color(255, 0, 0));
        btnCerrarPadre.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrarPadre.setText("CERRAR SESION");
        btnCerrarPadre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarPadre.addActionListener(this::btnCerrarPadreActionPerformed);

        btnReporte.setBackground(new java.awt.Color(51, 255, 51));
        btnReporte.setForeground(new java.awt.Color(0, 0, 0));
        btnReporte.setText("VER REPORTE DE HIJO");
        btnReporte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReporte.addActionListener(this::btnReporteActionPerformed);

        javax.swing.GroupLayout PanelPadreLayout = new javax.swing.GroupLayout(PanelPadre);
        PanelPadre.setLayout(PanelPadreLayout);
        PanelPadreLayout.setHorizontalGroup(
            PanelPadreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPadreLayout.createSequentialGroup()
                .addGroup(PanelPadreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPadreLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(lblNombrePadre, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelPadreLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(PanelPadreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCerrarPadre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(249, Short.MAX_VALUE))
        );
        PanelPadreLayout.setVerticalGroup(
            PanelPadreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPadreLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(PanelPadreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNombrePadre))
                .addGap(32, 32, 32)
                .addComponent(btnReporte)
                .addGap(18, 18, 18)
                .addComponent(btnCerrarPadre)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(PanelEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelPadre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelPadre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompletarMisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletarMisionActionPerformed
        misionActual.completar();
        estudiante.sumarPuntos(misionActual.getActividad().getPuntaje());
        lblPuntajeJu.setText(String.valueOf(estudiante.getPuntaje()));
        lblNivelJu.setText(String.valueOf(estudiante.getNivel()));
        nuevaMision();
         JOptionPane.showMessageDialog(this, "¡Misión completada!");
         guardarProgreso();
    }//GEN-LAST:event_btnCompletarMisionActionPerformed

    private void btnVerProgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProgresoActionPerformed
        JOptionPane.showMessageDialog(this,
    "Jugador: " + estudiante.getNombre() +
    "\nNivel: " + estudiante.getNivel() +
    "\nPuntaje: " + estudiante.getPuntaje());
    }//GEN-LAST:event_btnVerProgresoActionPerformed

    private void btnCerrarSrsionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSrsionActionPerformed
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCerrarSrsionActionPerformed

    private void btnRegristarAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegristarAlumnosActionPerformed
       docente.supervisarEstudiantes();
        String alumno = JOptionPane.showInputDialog(this, "Usuario del alumno:");
        String claveAlumno = JOptionPane.showInputDialog(this, "Contraseña del alumno:");
        
        String padre = JOptionPane.showInputDialog(this, "Usuario del padre:");
        String clavePadre = JOptionPane.showInputDialog(this, "Contraseña del padre:");


       if (alumno == null || claveAlumno == null || padre == null ||clavePadre == null ||alumno.isEmpty()||claveAlumno.isEmpty() ||padre.isEmpty()||clavePadre.isEmpty()){
           JOptionPane.showMessageDialog(this, "Datos inválidos");
           return;
       }
       try{
           java.io.FileWriter fw = new java.io.FileWriter("usuarios.txt", true);
           java.io.PrintWriter pw = new java.io.PrintWriter(fw);
           pw.println(alumno + "," + claveAlumno + ",ESTUDIANTE," + padre);
           pw.println(padre + "," + clavePadre + ",PADRE," + alumno);
           pw.close();
           JOptionPane.showMessageDialog(this, "Alumno registrado correctamente");
           cargarTablaUsuarios();
           cargarRelaciones();
       }catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Error al registrar");
       }
    }//GEN-LAST:event_btnRegristarAlumnosActionPerformed

    private void btnGestionarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarUsuariosActionPerformed
       docente.administrarMisiones();
try {
        java.io.BufferedReader br = new java.io.BufferedReader(
            new java.io.FileReader("usuarios.txt")
        );
        java.util.ArrayList<String> lista = new java.util.ArrayList<>();
        String linea;
        while((linea = br.readLine()) != null){
            lista.add(linea);
        }
        br.close();
        String[] opciones = new String[lista.size()];
        for(int i = 0; i < lista.size(); i++){
            opciones[i] = lista.get(i);
        }
        String seleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione usuario a eliminar:",
            "Gestión de usuarios",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        if(seleccionado == null){
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Eliminar usuario?\n" + seleccionado
        );
        if(confirm != JOptionPane.YES_OPTION){
            return;
        }
        java.io.PrintWriter pw = new java.io.PrintWriter("usuarios.txt");
        for(String u : lista){
            if(!u.equals(seleccionado)){
                pw.println(u);
            }
        }
        pw.close();
        JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error en gestión");
    }
    }//GEN-LAST:event_btnGestionarUsuariosActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnCerrarPadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarPadreActionPerformed
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCerrarPadreActionPerformed

    private void btnVerInsigniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerInsigniaActionPerformed
        String lista = "INSIGNIAS:\n\n";
        for(Insignia i : estudiante.getInsignias()){
            lista += i.getNombre() + " - " + i.getDescripcion() + "\n";
        }
        JOptionPane.showMessageDialog(this, lista);
    }//GEN-LAST:event_btnVerInsigniaActionPerformed

    private void btnVerReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerReportActionPerformed
        int fila = tablaUsuarios.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un alumno");
            return;
        }
        String nombreAlumno = tablaUsuarios.getValueAt(fila, 0).toString();
        Estudiante est = new Estudiante(nombreAlumno);
        this.estudiante = est;
        cargarProgreso();
        Reporte r = new Reporte();
        String resultado = r.generarReporte(est);
        JOptionPane.showMessageDialog(this, resultado);
    }//GEN-LAST:event_btnVerReportActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
       String nombrePadre = usuario.getUsuario();
       String hijo = "";
       try{
            BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split(",");
                if(datos.length == 4){
                   String usuarioArchivo = datos[0];
                   String rol = datos[2];
                   String relacion = datos[3];
                   if(rol.equalsIgnoreCase("PADRE") && usuarioArchivo.equals(nombrePadre)){
                     hijo = relacion;  
                   }
                }
            }
            br.close();
       }catch(Exception e){
           System.out.println("Error al buscar hijo");
       }
       if(hijo.equals("")){
           JOptionPane.showMessageDialog(this, "No se encontró hijo");
        return;
       }
       Estudiante est = new Estudiante(hijo);
       this.estudiante = est;
       cargarProgreso();
       String resultado = "===== REPORTE DEL HIJO =====\n\n";
       resultado += "Alumno: " + est.getNombre() + "\n";
       resultado += "Nivel: " + est.getNivel() + "\n";
       resultado += "Puntaje: " + est.getPuntaje();
       JOptionPane.showMessageDialog(this, resultado);
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
         if(actividadActual == null){
             JOptionPane.showMessageDialog(this, "Primero genera una misión");
             return;
         }
         try {
             String ruta = System.getProperty("user.dir")
                + File.separator
                     + actividadActual.getVideo();
             Runtime.getRuntime().exec("cmd /c start \"\" \"" + ruta + "\"");
         }catch (Exception e) {
             System.out.println("Error: " + e.getMessage());
         }
    }//GEN-LAST:event_btnVerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAdmin;
    private javax.swing.JPanel PanelEstudiante;
    private javax.swing.JPanel PanelPadre;
    private javax.swing.JButton btnCerrarPadre;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCerrarSrsion;
    private javax.swing.JButton btnCompletarMision;
    private javax.swing.JButton btnGestionarUsuarios;
    private javax.swing.JButton btnRegristarAlumnos;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnVer;
    private javax.swing.JButton btnVerInsignia;
    private javax.swing.JButton btnVerProgreso;
    private javax.swing.JButton btnVerReport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMision;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNivelJu;
    private javax.swing.JLabel lblNombreA;
    private javax.swing.JLabel lblNombreAdministrador;
    private javax.swing.JLabel lblNombreJugador;
    private javax.swing.JLabel lblNombrePadre;
    private javax.swing.JLabel lblPuntaje;
    private javax.swing.JLabel lblPuntajeJu;
    private javax.swing.JLabel lbltitulo;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextArea txtRelacion;
    // End of variables declaration//GEN-END:variables
}
