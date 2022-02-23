import { Component, OnInit } from '@angular/core';
import { TempService } from 'src/app/services/temp.service';
import FileSaver from 'file-saver';
import { DMSFolder } from 'src/app/interfaces/dms-folder';
import { DMSFile } from 'src/app/interfaces/dms-file';

@Component({
  selector: 'app-temp',
  templateUrl: './temp.component.html',
  styleUrls: ['./temp.component.css']
})
export class TempComponent implements OnInit {

  constructor(private tempService: TempService) { }

  public root!: DMSFolder;

  public currentFolder!: DMSFolder;
  public fullCurrentPath!: string;

  public previousFolders: DMSFolder[] = [];
  public previousPaths: string[] = [];

  ngOnInit(): void {
    this.tempService.getFileStructure().subscribe(res => { 
      this.currentFolder = this.root = this.currentFolder = res;
      this.fullCurrentPath = this.currentFolder.name;
    });
  }

  public navigateTo(folder: DMSFolder) {
    this.previousPaths.push(this.fullCurrentPath);
    this.previousFolders.push(this.currentFolder);
    this.fullCurrentPath += '\\' + folder.name;
    this.currentFolder = folder;
    console.log("PREVIOUS FOLDERS: ", this.previousFolders);
    console.log("PATHS: ", this.previousPaths);
    console.log("CURRENT FOLDER: ", this.currentFolder);
    console.log("CURRENT PATH: ", this.fullCurrentPath);
  }

  public goHome() {
    this.navigateTo(this.root);
    this.fullCurrentPath = this.root.name;
    console.log("PREVIOUS FOLDERS: ", this.previousFolders);
    console.log("PATHS: ", this.previousPaths);
    console.log("CURRENT FOLDER: ", this.currentFolder);
    console.log("CURRENT PATH: ", this.fullCurrentPath);
  }

  public goBack() {
    let previousFolder = this.previousFolders.pop();
    let previousPath = this.previousPaths.pop();

    if (!previousFolder && !previousPath) {
      return;
    }

    this.currentFolder = previousFolder!;
    this.fullCurrentPath = previousPath!;
    console.log("PREVIOUS FOLDERS: ", this.previousFolders);
    console.log("PATHS: ", this.previousPaths);
    console.log("CURRENT FOLDER: ", this.currentFolder);
    console.log("CURRENT PATH: ", this.fullCurrentPath);
  }

  public deleteFile(file: DMSFile) {
    this.tempService.delete(this.fullCurrentPath, file.name)
                    .subscribe(res => { 
                      if (res == true) {
                        this.currentFolder.files = this.currentFolder.files.filter(f => f != file) 
                      }
                    } 
                  );
  }

  public deleteFolder(folder: DMSFolder) {
    this.tempService.delete(this.fullCurrentPath, folder.name)
                    .subscribe(res => {
                      if (res == true) {
                        this.currentFolder.folders = this.currentFolder.folders.filter(f => f != folder);
                      }
                    }
                  );
  }

  public updateFile(name: any) {
    console.log("UPDATE FILE " + name);

  }

  public uploadFile() {
    console.log("UPLOAD FILE");
  }

  public logOut() {
    this.tempService.logOut();
  }

  public base64ToArrayBuffer(base64: string) {
    var binaryString =  window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++)        {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }

    return bytes;
  }

  public saveByteArray(file: DMSFile) {
    var blob = new Blob([this.base64ToArrayBuffer(file.content)], {type: "octet/stream"});
    FileSaver.saveAs(blob, file.name);
  }
}


