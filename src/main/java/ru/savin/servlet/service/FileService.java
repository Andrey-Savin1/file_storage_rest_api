package ru.savin.servlet.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.savin.servlet.dto.FileDto;
import ru.savin.servlet.model.File;
import ru.savin.servlet.repository.FileRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public File createFile(File file) {
        return fileRepository.create(file);
    }

    public File getFileById(Long id) {
        return fileRepository.getById(id);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    public File updateFile(File post) {
        return fileRepository.update(post);
    }

    public List<File> getAllFiles() {

        return fileRepository.getAll();

    }

    public FileDto saveFileToPath(HttpServletRequest req) throws Exception {

        DiskFileItemFactory factory = new DiskFileItemFactory();//Disk object
        factory.setRepository(new java.io.File("C:\\Users\\Centr\\Desktop\\test")); //временная директория
        factory.setSizeThreshold(1024 * 8); //размер трешхолда

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        //  upload.setFileSizeMax(1024 * 1024 * 20);//максимальный размрер файла
        upload.setSizeMax(1024 * 1024 * 20);// максимальный обьем загружаемых данных?
        //String path = getServletContext().getRealPath("");//получаем каталог в который нужно сохранить файл

            List<FileItem> list = upload.parseRequest(req);
            String absolutePath = null;
            String fileName = null;
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String va = item.getString("UTF-8");
                    System.out.println(va);
                } else {
                    fileName = item.getName();
                    java.io.File fileDir = new java.io.File("C:\\Work\\file_storage_rest_api\\src\\main\\resources\\files\\");
                    if (!fileDir.isDirectory()) {
                        fileDir.mkdir();
                    }
                    var file = new java.io.File("C:\\Work\\file_storage_rest_api\\src\\main\\resources\\files\\" + fileName);
                    absolutePath = file.getAbsolutePath();
                    item.write(file);
                }
            }
            return FileDto.builder()
                    .name(fileName)
                    .filePath(absolutePath).build();

    }
}
