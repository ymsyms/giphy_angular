import { Component } from '@angular/core';
import {GiphyService} from './giphyService';
import {GiphyModel} from './giphyModel';
import {NgForm} from '@angular/forms';
import {EventEmitter} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{

  list: GiphyModel[] = [];
  userid: String;

  constructor(private giphyService: GiphyService) { }

  getList(form: NgForm){
    this.list = [];

    this.giphyService.getLikeGiphys(form.value.userid)
      .then((data) => {
        for(let i =0;i<data.length;i++) {
          let m: GiphyModel = {
            url:data[i]['url'],
            title:data[i]['title'],
            like:true
          };
          this.list.push(m);
        }
      });
  }

  processSearch(form: NgForm) {
    this.userid = form.value.userid;
    this.list = [];

    this.giphyService.giphySearch(form.value.searchWords)
      .then((data) => {
        for(let i=0;i<data['data'].length;i++) {
          let m : GiphyModel = {
            url : data['data'][i]['images']['downsized']['url'],
            title : data['data'][i]['title']
          };
          this.list.push(m);
        }
      })
  }
}
