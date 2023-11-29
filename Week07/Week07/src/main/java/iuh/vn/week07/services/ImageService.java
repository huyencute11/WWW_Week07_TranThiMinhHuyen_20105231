package iuh.vn.week07.services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import iuh.vn.week07.DTOs.Errors.ErrorModel;
import iuh.vn.week07.Models.ProductImage;
import iuh.vn.week07.Reponsitories.ProductImageReponsitory;

@Service
public class ImageService {

    private ProductImageReponsitory productImageReponsitory;

    private final Path fileStorageLocation;

    @Autowired
    public ImageService( ProductImageReponsitory productImageReponsitory) {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
        }
        this.productImageReponsitory = productImageReponsitory;
    }


    public List<ProductImage> UploadProductImage(MultipartFile[] files, ErrorModel errors) {
        try {
            List<ProductImage> productImages = new ArrayList<ProductImage>();
            if (files.length != 0) {
                for (MultipartFile file : files) {
                    String fileName = file.getName() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path destinationFile = this.fileStorageLocation.resolve(
                        Paths.get(fileName))
                        .normalize().toAbsolutePath();
                    if (!destinationFile.getParent().equals(this.fileStorageLocation.toAbsolutePath())) {
                        // This is a security check
                        errors.Add("Cannot store file outside current directory.");
                    }
                    try (InputStream inputStream = file.getInputStream()) {
                        Files.copy(inputStream, destinationFile,
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                    ProductImage productImage = new ProductImage();
                    productImage.setPath(destinationFile.getFileName().toString());
                    productImages.add(productImage);
                }
                productImages = (List<ProductImage>)productImageReponsitory.saveAll(productImages);
            }
            return productImages;
        } catch (Exception e) {
            errors.Add("Something went wrong.");
            return null;
        }
    }
    
    public Resource loadFileAsResource(String fileName, ErrorModel errors) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                errors.Add("File not found " + fileName);
            }
        } catch (Exception ex) {
            errors.Add("File not found " + fileName);
        }
        return null;
    }
    
    public void delete(String fileName){
        try {
            Path destinationFile = this.fileStorageLocation.resolve(
                Paths.get(fileName))
                .normalize().toAbsolutePath();
            Files.delete(destinationFile);
        } catch(Exception e) {}
    }

    // public String storeFile(MultipartFile file) {
    //     // Normalize file name
    //     String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    //     try {
    //         Path targetLocation = this.fileStorageLocation.resolve(fileName);
    //         Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    //         return fileName;
    //     } catch (Exception ex) {
    //         return null;
    //     }
    // }
}
