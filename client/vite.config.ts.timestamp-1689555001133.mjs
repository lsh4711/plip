// vite.config.ts
import react from "file:///D:/44-main/seb44_main_012/client/node_modules/@vitejs/plugin-react/dist/index.mjs";
import { defineConfig } from "file:///D:/44-main/seb44_main_012/client/node_modules/vite/dist/node/index.js";
import { compression } from "file:///D:/44-main/seb44_main_012/client/node_modules/vite-plugin-compression2/dist/index.mjs";
import svgr from "file:///D:/44-main/seb44_main_012/client/node_modules/vite-plugin-svgr/dist/index.js";
import * as path from "path";
var __vite_injected_original_dirname = "D:\\44-main\\seb44_main_012\\client";
var vite_config_default = defineConfig({
  plugins: [
    react(),
    svgr(),
    compression({
      include: [/\.(js)$/, /\.(css)$/, /\.(webp)$/],
      threshold: 1400
    })
  ],
  resolve: {
    alias: [{ find: "@", replacement: path.resolve(__vite_injected_original_dirname, "src") }]
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFw0NC1tYWluXFxcXHNlYjQ0X21haW5fMDEyXFxcXGNsaWVudFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcNDQtbWFpblxcXFxzZWI0NF9tYWluXzAxMlxcXFxjbGllbnRcXFxcdml0ZS5jb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6LzQ0LW1haW4vc2ViNDRfbWFpbl8wMTIvY2xpZW50L3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHJlYWN0IGZyb20gJ0B2aXRlanMvcGx1Z2luLXJlYWN0JztcclxuaW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSc7XHJcbmltcG9ydCB7IGNvbXByZXNzaW9uIH0gZnJvbSAndml0ZS1wbHVnaW4tY29tcHJlc3Npb24yJztcclxuaW1wb3J0IHN2Z3IgZnJvbSAndml0ZS1wbHVnaW4tc3Zncic7XHJcblxyXG5pbXBvcnQgKiBhcyBwYXRoIGZyb20gJ3BhdGgnO1xyXG5cclxuLy8gaHR0cHM6Ly92aXRlanMuZGV2L2NvbmZpZy9cclxuZXhwb3J0IGRlZmF1bHQgZGVmaW5lQ29uZmlnKHtcclxuICBwbHVnaW5zOiBbXHJcbiAgICByZWFjdCgpLFxyXG4gICAgc3ZncigpLFxyXG4gICAgY29tcHJlc3Npb24oe1xyXG4gICAgICBpbmNsdWRlOiBbL1xcLihqcykkLywgL1xcLihjc3MpJC8sIC9cXC4od2VicCkkL10sXHJcbiAgICAgIHRocmVzaG9sZDogMTQwMCxcclxuICAgIH0pLFxyXG4gIF0sXHJcbiAgcmVzb2x2ZToge1xyXG4gICAgYWxpYXM6IFt7IGZpbmQ6ICdAJywgcmVwbGFjZW1lbnQ6IHBhdGgucmVzb2x2ZShfX2Rpcm5hbWUsICdzcmMnKSB9XSxcclxuICB9LFxyXG59KTtcclxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUEwUixPQUFPLFdBQVc7QUFDNVMsU0FBUyxvQkFBb0I7QUFDN0IsU0FBUyxtQkFBbUI7QUFDNUIsT0FBTyxVQUFVO0FBRWpCLFlBQVksVUFBVTtBQUx0QixJQUFNLG1DQUFtQztBQVF6QyxJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixTQUFTO0FBQUEsSUFDUCxNQUFNO0FBQUEsSUFDTixLQUFLO0FBQUEsSUFDTCxZQUFZO0FBQUEsTUFDVixTQUFTLENBQUMsV0FBVyxZQUFZLFdBQVc7QUFBQSxNQUM1QyxXQUFXO0FBQUEsSUFDYixDQUFDO0FBQUEsRUFDSDtBQUFBLEVBQ0EsU0FBUztBQUFBLElBQ1AsT0FBTyxDQUFDLEVBQUUsTUFBTSxLQUFLLGFBQWtCLGFBQVEsa0NBQVcsS0FBSyxFQUFFLENBQUM7QUFBQSxFQUNwRTtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbXQp9Cg==
