package com.rest.frontend;


import Objects.API;
import Objects.Productotemp;
import com.vaadin.flow.component.UI;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Route(value = "importarxcl", layout = MainView.class)
public class VistaCargarArchivo extends VerticalLayout {

    private Upload singleFileUpload;

    public VistaCargarArchivo() throws Exception {
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        singleFileUpload = new Upload(memoryBuffer);
        add(singleFileUpload);

        singleFileUpload.addSucceededListener(event -> {
            try {
                 Label aviso =new Label("Se importaron los siguientes productos, modifique su stock, ubicación, nombre comercial y precio desde el inventario:");
                add(aviso);
                 // Obtiene información sobre el archivo cargado
                InputStream fileData = memoryBuffer.getInputStream();
                String fileName = event.getFileName();
                long contentLength = event.getContentLength();
                String mimeType = event.getMIMEType();

                // Procesa el archivo cargado
                List<Productotemp> products = processFile(fileData, fileName);

                // Muestra los productos en el DataGridView
                UI.getCurrent().access(() -> showProductsInDataGridView(products));

            } catch (Exception e) {
                e.printStackTrace();
                Notification.show("Error al procesar el archivo: " + e.getMessage(), 5000, Notification.Position.BOTTOM_CENTER);
            }
        });
    }

    private List<Productotemp> processFile(InputStream fileData, String fileName) throws Exception {
        // Guarda el archivo en la carpeta "resources"
        Path resourcesDirectory = Paths.get("src", "main", "resources");
        Path filePath = resourcesDirectory.resolve(fileName);
        Files.copy(fileData, filePath, StandardCopyOption.REPLACE_EXISTING);

        // Procesa el archivo de Excel aquí
        try (Workbook workbook = new XSSFWorkbook(filePath.toFile())) {
            List<Productotemp> products = convertXlsxToJavaObjects(workbook);

            Grid<Productotemp> tabla=new Grid<>();
            Grid.Column<Productotemp> namecol =tabla.addColumn(Productotemp::getProvider).setHeader("PROVEEDOR:");
            Grid.Column<Productotemp> preciocol =tabla.addColumn(Productotemp::getReference).setHeader("REFERENCIA:");
            Grid.Column<Productotemp> stockcol =tabla.addColumn(Productotemp::getMinOrderQty).setHeader("CANTIDAD MÍNIMA PARA PEDIDO REAPROVISIONAMIENTO:");

            tabla.setItems(products);
            add(tabla);

            return products;
        }
    }

    private void showProductsInDataGridView(List<Productotemp> products) {
        Grid<Productotemp> grid = new Grid<>(Productotemp.class);
        grid.setItems(products);

        // Configura las columnas
        grid.addColumn("PROVEEDOR").setHeader("PROVEEDOR").setSortable(true).setKey("provider");
        grid.addColumn("REFERENCIA").setHeader("REFERENCIA").setSortable(true).setKey("reference");
        grid.addColumn("CANTIDAD MÍNIMA PARA PEDIDO REAPROVISIONAMIENTO")
                .setHeader("CANTIDAD MÍNIMA PARA PEDIDO REAPROVISIONAMIENTO")
                .setSortable(true)
                .setKey("minOrderQty");

        // Remueve el Upload y agrega el DataGridView a la vista actual
        remove(singleFileUpload);
        add(grid);
    }

    public List<Productotemp> convertXlsxToJavaObjects(Workbook workbook) throws IOException {
        List<Productotemp> products = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        API api=new API();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Skip header row
                continue;
            }

            String provider = row.getCell(0).getStringCellValue();
            String reference = row.getCell(1).getStringCellValue();
            String minOrderQty = String.valueOf( (int)row.getCell(2).getNumericCellValue() );
            try {
                api.createproduct(reference,"0","0","NA",provider,"NA",minOrderQty);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            products.add(new Productotemp(provider, reference, minOrderQty));
        }

        for (Productotemp producto : products) {
            System.out.println(producto);
        }

        return products;
    }
}