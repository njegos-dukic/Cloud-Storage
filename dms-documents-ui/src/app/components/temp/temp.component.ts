import { Component, OnInit } from '@angular/core';
import { TempService } from 'src/app/services/temp.service';
import FileSaver from 'file-saver';

@Component({
  selector: 'app-temp',
  templateUrl: './temp.component.html',
  styleUrls: ['./temp.component.css']
})
export class TempComponent implements OnInit {

  constructor(private tempService: TempService) { }

  public response: any;
  public rootFiles: any;
  public rootFolders: any; 

  public current: any;
  public files: any;
  public folders: any;

  ngOnInit(): void {
    this.response = this.tempService.getDirectories().subscribe(r => { 
      console.log(r); 
      this.current = r.name;
      this.files = this.rootFiles = r.files; 
      this.folders = this.rootFolders = r.folders;
    });
  }

  public downloadFile() {
    console.log("FILES");
  }

  public updateFolders(folder: any) {

    this.current = folder.name;
    this.files = folder.files;
    this.folders = folder.folders;
  }

  public goHome() {

    this.files = this.rootFiles;
    this.folders = this.rootFolders;
  }

  public base64ToArrayBuffer(base64: any) {
    
    var binaryString =  window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++)        {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
  }

  public saveByteArray(data: any, name: any) {

    var blob = new Blob([this.base64ToArrayBuffer(data)], {type: "octet/stream"});
    FileSaver.saveAs(blob, name);
  }
}


